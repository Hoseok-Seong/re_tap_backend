package hoselabs.future_letter.domain.home.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hoselabs.future_letter.domain.home.dto.HomeResp;
import hoselabs.future_letter.domain.home.util.QuoteProvider;
import hoselabs.future_letter.domain.letter.dao.LetterRepository;
import hoselabs.future_letter.domain.letter.entity.Letter;
import hoselabs.future_letter.domain.setup.MockTest;
import hoselabs.future_letter.domain.user.entity.User;
import hoselabs.future_letter.global.security.MyUserDetails;
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
    private LetterRepository letterRepository;

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
        HomeResp.Quote quote = new HomeResp.Quote("Steve Jobs", "한국어 명언", "English quote");

        Letter letter1 = Letter.builder()
                .title("첫 번째 편지")
                .build();
        ReflectionTestUtils.setField(letter1, "createdAt", LocalDateTime.of(2024, 5, 1, 10, 0));

        Letter letter2 = Letter.builder()
                .title("두 번째 편지")
                .build();
        ReflectionTestUtils.setField(letter2, "createdAt", LocalDateTime.of(2024, 4, 30, 10, 0));

        List<Letter> recentLetters = List.of(letter1, letter2);

        List<Letter> upcomingLetters = List.of(
                Letter.builder()
                        .title("다가올 편지")
                        .arrivalDate(LocalDateTime.of(2024, 5, 25, 0, 0))
                        .build()
        );

        given(letterRepository.countUnreadAndArrived(user.getId())).willReturn(unreadCount);
        given(quoteProvider.getQuoteByDayOfYear(anyInt())).willReturn(quote);
        given(letterRepository.findRecentLetters(user.getId(), 3)).willReturn(recentLetters);
        given(letterRepository.findUpcomingLetters(eq(user.getId()), any(), any(), eq(3))).willReturn(upcomingLetters);

        // when
        HomeResp result = homeService.getHome(user.getId());

        // then
        assertThat(result).isNotNull();

        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        System.out.println("HomeResp JSON:\n" + json);

        assertThat(result.getUnreadCount()).isEqualTo(unreadCount);
        assertThat(result.getTodayQuote().getAuthor()).isEqualTo("Steve Jobs");

        assertThat(result.getRecentLetters()).hasSize(2);
        assertThat(result.getRecentLetters().get(0).getTitle()).isEqualTo("첫 번째 편지");

        assertThat(result.getUpcomingLetters()).hasSize(1);
        assertThat(result.getUpcomingLetters().get(0).getTitle()).isEqualTo("다가올 편지");
    }
}
