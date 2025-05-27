package hoselabs.re_tap.domain.goal.service;

import hoselabs.re_tap.domain.goal.dao.GoalRepository;
import hoselabs.re_tap.domain.goal.dto.GoalCreateReq;
import hoselabs.re_tap.domain.goal.dto.GoalCreateResp;
import hoselabs.re_tap.domain.goal.dto.GoalDetailResp;
import hoselabs.re_tap.domain.goal.dto.GoalListResp;
import hoselabs.re_tap.domain.goal.entity.Goal;
import hoselabs.re_tap.domain.goal.entity.GoalStatus;
import hoselabs.re_tap.domain.goal.exception.GoalNotArrivedException;
import hoselabs.re_tap.domain.goal.exception.GoalNotFoundException;
import hoselabs.re_tap.domain.setup.MockTest;
import hoselabs.re_tap.domain.user.entity.User;
import hoselabs.re_tap.global.error.exception.UserDetailsException;
import hoselabs.re_tap.global.security.MyUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

public class GoalServiceTest extends MockTest {

    @InjectMocks
    private GoalService goalService;

    @Mock
    private GoalRepository goalRepository;

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

    @Test
    void 목표_작성_성공() {
        // given
        GoalCreateReq req = new GoalCreateReq(null, "제목", "내용", false, LocalDateTime.now(), true);

        Goal saved = Goal.builder()
                .id(123L)
                .userId(user.getId())
                .title(req.getTitle())
                .content(req.getContent())
                .status(GoalStatus.DELIVERED)
                .build();

        given(goalRepository.save(any())).willReturn(saved);

        // when
        GoalCreateResp result = goalService.createGoal(userDetails, req);

        // then
        assertThat(result.getGoalId()).isEqualTo(123L);
    }

    @Test
    void 유저정보가_없으면_예외() {
        // given
        GoalCreateReq req = new GoalCreateReq(null,"제목", "내용", false, LocalDateTime.now(), true);

        // when & then
        assertThatThrownBy(() -> goalService.createGoal(null, req))
                .isInstanceOf(UserDetailsException.class);
    }

    @Test
    void 목표_목록_조회_성공() {
        // given
        Goal unreadgoal = Goal.builder()
                .id(1L)
                .userId(user.getId())
                .title("첫 번째 목표")
                .arrivalDate(LocalDateTime.now().minusDays(1))
                .isLocked(false)
                .build(); // readAt = null → 읽지 않음

        Goal readgoal = Goal.builder()
                .id(2L)
                .userId(user.getId())
                .title("두 번째 목표")
                .arrivalDate(LocalDateTime.now().plusDays(1))
                .isLocked(true)
                .build();

        // 읽음 처리
        readgoal.markAsRead(); // 읽은 상태로 처리

        List<Goal> goals = List.of(unreadgoal, readgoal);

        given(goalRepository.findAllByUserIdSorted(user.getId())).willReturn(goals);

        // when
        GoalListResp result = goalService.getGoals(user.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getGoals()).hasSize(2);

        GoalListResp.GoalSummary first = result.getGoals().get(0);
        assertThat(first.getGoalId()).isEqualTo(1L);
        assertThat(first.getTitle()).isEqualTo("첫 번째 목표");
        assertThat(first.getIsRead()).isFalse();
        assertThat(first.getIsArrived()).isTrue();
    }

    @Test
    void 목표_도착_읽지않음_조회_성공() {
        // given
        Long goalId = 100L;
        Goal goal = Goal.builder()
                .id(goalId)
                .userId(user.getId())
                .title("도착한 목표")
                .content("내용입니다")
                .arrivalDate(LocalDateTime.now().minusDays(1))
                .isLocked(false)
                .status(GoalStatus.DELIVERED)
                .build();

        given(goalRepository.findByIdAndUserId(goalId, user.getId()))
                .willReturn(Optional.of(goal));

        // when
        GoalDetailResp result = goalService.getGoal(user.getId(), goalId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getGoalId()).isEqualTo(goalId);
        assertThat(result.getIsRead()).isTrue();
        assertThat(result.getReadAt()).isNotNull(); // 읽은 시간 기록됨
    }

    @Test
    void 목표_도착_이미읽음_조회_성공() {
        // given
        Long goalId = 101L;
        LocalDateTime readTime = LocalDateTime.now().minusHours(1);

        Goal goal = Goal.builder()
                .id(goalId)
                .userId(user.getId())
                .title("읽은 목표")
                .content("이미 읽었어요")
                .arrivalDate(LocalDateTime.now().minusDays(2))
                .isLocked(false)
                .status(GoalStatus.DELIVERED)
                .build();

        goal.markAsRead(readTime);

        given(goalRepository.findByIdAndUserId(goalId, user.getId()))
                .willReturn(Optional.of(goal));

        // when
        GoalDetailResp result = goalService.getGoal(user.getId(), goalId);

        // then
        assertThat(result.getIsRead()).isTrue();
        assertThat(result.getReadAt()).isEqualTo(readTime); // 기존 readAt 유지
    }

    @Test
    void 목표_없으면_예외() {
        // given
        Long goalId = 999L;
        given(goalRepository.findByIdAndUserId(goalId, user.getId()))
                .willReturn(Optional.empty());

        // expect
        assertThatThrownBy(() -> goalService.getGoal(user.getId(), goalId))
                .isInstanceOf(GoalNotFoundException.class);
    }

    @Test
    void 목표_도착_전이면_예외() {
        // given
        Long goalId = 102L;
        Goal goal = Goal.builder()
                .id(goalId)
                .userId(user.getId())
                .title("미래의 목표")
                .content("아직 도착 안했어요")
                .arrivalDate(LocalDateTime.now().plusDays(1))
                .isLocked(true)
                .status(GoalStatus.SCHEDULED)
                .build();

        given(goalRepository.findByIdAndUserId(goalId, user.getId()))
                .willReturn(Optional.of(goal));

        // expect
        assertThatThrownBy(() -> goalService.getGoal(user.getId(), goalId))
                .isInstanceOf(GoalNotArrivedException.class);
    }

    @Test
    void 목표_임시저장_후_수정_성공() {
        // given
        Long goalId = 200L;
        GoalCreateReq req = new GoalCreateReq(
                goalId,
                "수정된 제목",
                "수정된 내용",
                false,
                LocalDateTime.now().plusDays(2),
                false
        );

        Goal original = Goal.builder()
                .id(goalId)
                .userId(user.getId())
                .title("원래 제목")
                .content("원래 내용")
                .arrivalDate(LocalDateTime.now().plusDays(1))
                .isLocked(false)
                .status(GoalStatus.SCHEDULED)
                .build();

        given(goalRepository.findById(goalId)).willReturn(Optional.of(original));

        // when
        GoalCreateResp result = goalService.createGoal(userDetails, req);

        // then
        assertThat(result.getGoalId()).isEqualTo(goalId);
        assertThat(original.getTitle()).isEqualTo("수정된 제목");
        assertThat(original.getContent()).isEqualTo("수정된 내용");
        assertThat(original.getArrivalDate()).isEqualTo(req.getArrivalDate());
        assertThat(original.getStatus()).isEqualTo(GoalStatus.DRAFT);
    }
}
