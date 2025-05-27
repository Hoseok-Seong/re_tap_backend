package hoselabs.re_tap.domain.home.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hoselabs.re_tap.domain.goal.dao.GoalRepository;
import hoselabs.re_tap.domain.goal.entity.Goal;
import hoselabs.re_tap.domain.home.dto.HomeResp;
import hoselabs.re_tap.domain.home.util.QuoteProvider;
import hoselabs.re_tap.domain.setup.MockTest;
import hoselabs.re_tap.domain.user.entity.User;
import hoselabs.re_tap.global.security.MyUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

public class HomeServiceTest extends MockTest {

    @InjectMocks
    private HomeService homeService;

    @Mock
    private GoalRepository goalRepository;

    @Mock
    private QuoteProvider quoteProvider;

    private User user;
    private MyUserDetails userDetails;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("testuser")
                .provider("kakao")
                .build();

        userDetails = new MyUserDetails(user);
    }

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void 홈_화면_데이터_정상_조회() throws Exception {
        // given
        int unreadCount = 2;
        HomeResp.Quote quote = new HomeResp.Quote("1", "Steve Jobs", "한국어 명언", "English quote", "subject");

        Goal goal1 = Goal.builder()
                .title("첫 번째 목표")
                .build();
        ReflectionTestUtils.setField(goal1, "createdAt", LocalDateTime.of(2024, 5, 1, 10, 0));

        Goal goal2 = Goal.builder()
                .title("두 번째 목표")
                .build();
        ReflectionTestUtils.setField(goal2, "createdAt", LocalDateTime.of(2024, 4, 30, 10, 0));

        List<Goal> recentGoals = List.of(goal1, goal2);

        List<Goal> upcomingGoals = List.of(
                Goal.builder()
                        .title("다가올 목표")
                        .arrivalDate(LocalDateTime.of(2024, 5, 25, 0, 0))
                        .build()
        );

        given(goalRepository.countUnreadAndArrived(user.getId())).willReturn(unreadCount);
        given(quoteProvider.getQuoteByDayOfYear(anyInt())).willReturn(quote);
        given(goalRepository.findRecentGoals(user.getId(), 3)).willReturn(recentGoals);
        given(goalRepository.findUpcomingGoals(eq(user.getId()), any(), any(), eq(3))).willReturn(upcomingGoals);

        // when
        HomeResp result = homeService.getHome(user.getId());

        // then
        assertThat(result).isNotNull();

        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        System.out.println("HomeResp JSON:\n" + json);

        assertThat(result.getUnreadCount()).isEqualTo(unreadCount);
        assertThat(result.getTodayQuote().getAuthor()).isEqualTo("Steve Jobs");

        assertThat(result.getRecentGoals()).hasSize(2);
        assertThat(result.getRecentGoals().get(0).getTitle()).isEqualTo("첫 번째 목표");

        assertThat(result.getUpcomingGoals()).hasSize(1);
        assertThat(result.getUpcomingGoals().get(0).getTitle()).isEqualTo("다가올 목표");
    }
}
