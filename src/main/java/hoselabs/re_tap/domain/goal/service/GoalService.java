package hoselabs.re_tap.domain.goal.service;

import hoselabs.re_tap.domain.goal.dao.GoalRepository;
import hoselabs.re_tap.domain.goal.dto.GoalCreateReq;
import hoselabs.re_tap.domain.goal.dto.GoalCreateResp;
import hoselabs.re_tap.domain.goal.dto.GoalDeleteReq;
import hoselabs.re_tap.domain.goal.dto.GoalDeleteResp;
import hoselabs.re_tap.domain.goal.dto.GoalDetailResp;
import hoselabs.re_tap.domain.goal.dto.GoalFeedbackReq;
import hoselabs.re_tap.domain.goal.dto.GoalFeedbackResp;
import hoselabs.re_tap.domain.goal.dto.GoalListResp;
import hoselabs.re_tap.domain.goal.entity.Goal;
import hoselabs.re_tap.domain.goal.entity.GoalStatus;
import hoselabs.re_tap.domain.goal.exception.GoalNotArrivedException;
import hoselabs.re_tap.domain.goal.exception.GoalNotFoundException;
import hoselabs.re_tap.domain.goal.exception.GoalNotOwnedException;
import hoselabs.re_tap.domain.user.exception.UserNotOwnerException;
import hoselabs.re_tap.global.error.exception.UserDetailsException;
import hoselabs.re_tap.global.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;

    @Transactional
    public GoalCreateResp createGoal(final MyUserDetails myUserDetails, final GoalCreateReq goalCreateReq) {
        if (myUserDetails == null) {
            throw new UserDetailsException();
        }

        GoalStatus status = determineStatus(goalCreateReq.getIsSend(), goalCreateReq.getArrivalDate());

        Long userId = myUserDetails.getUser().getId();

        // ID가 null이 아니면 수정
        if (goalCreateReq.getGoalId() != null) {
            Goal goal = goalRepository.findById(goalCreateReq.getGoalId())
                    .orElseThrow(() -> new GoalNotFoundException(goalCreateReq.getGoalId()));

            if (!goal.getUserId().equals(userId)) {
                throw new UserNotOwnerException();
            }

            goalCreateReq.updateEntity(goal, status);
            return new GoalCreateResp(goal.getId()); // 수정 후 id 반환
        }

        // 새 목표 생성
        Goal saved = goalRepository.save(goalCreateReq.toEntity(userId, status));
        return new GoalCreateResp(saved.getId());
    }

    private GoalStatus determineStatus(Boolean isSend, LocalDateTime arrivalDate) {
        if (!isSend) {
            return GoalStatus.DRAFT;
        }
        if (arrivalDate != null) {
            return GoalStatus.SCHEDULED;
        }
        return GoalStatus.DELIVERED;
    }

    @Transactional(readOnly = true)
    public GoalListResp getGoals(Long userId) {
        List<Goal> goals = goalRepository.findAllByUserIdSorted(userId);

        List<GoalListResp.GoalSummary> summaries = goals.stream()
                .map(goal -> new GoalListResp.GoalSummary(
                        goal.getId(),
                        goal.getTitle(),
                        goal.getContent(),
                        goal.getArrivalDate() != null ? goal.getArrivalDate().toLocalDate() : null,
                        goal.getIsLocked(),
                        goal.getArrivalDate() != null && goal.getArrivalDate().isBefore(LocalDateTime.now()),
                        goal.getReadAt() != null,
                        goal.getCreatedAt().toLocalDate(),
                        goal.getStatus()
                ))
                .toList();

        return new GoalListResp(summaries);
    }

    @Transactional
    public GoalDetailResp getGoal(Long userId, Long goalId) {
        Goal goal = goalRepository.findByIdAndUserId(goalId, userId)
                .orElseThrow(() -> new GoalNotFoundException(goalId));

        if (goal.getStatus() == GoalStatus.SCHEDULED && !goal.isArrived() && goal.getIsLocked()) {
            throw new GoalNotArrivedException();
        }

        if (!goal.isRead()) {
            goal.markAsRead();
        }

        return new GoalDetailResp(
                goal.getId(),
                goal.getTitle(),
                goal.getContent(),
                goal.getArrivalDate() != null ? goal.getArrivalDate().toLocalDate() : null,
                goal.getIsLocked(),
                goal.isArrived(),
                goal.isRead(),
                goal.getReadAt(),
                goal.getStatus()
        );
    }

    @Transactional
    public GoalDeleteResp deleteGoals(final MyUserDetails myUserDetails, final GoalDeleteReq goalDeleteReq) {
        List<Goal> goals = goalRepository.findAllById(goalDeleteReq.getGoalIds());

        boolean hasOthers = goals.stream()
                .anyMatch(goal -> !goal.getUserId().equals(myUserDetails.getUser().getId()));

        if (hasOthers) {
            throw new GoalNotOwnedException();
        }

        goalRepository.deleteAll(goals);

        return new GoalDeleteResp("목표 삭제가 완료되었습니다.");
    }

    @Transactional
    public GoalFeedbackResp feedbackGoal(final MyUserDetails myUserDetails, final GoalFeedbackReq goalFeedbackReq) {
        if (myUserDetails == null) {
            throw new UserDetailsException();
        }

        Long userId = myUserDetails.getUser().getId();
        Long goalId = goalFeedbackReq.getGoalId();

        Goal goal = goalRepository.findByIdAndUserId(goalId, userId)
                .orElseThrow(() -> new GoalNotFoundException(goalId));

        goal.feedbackGoal(goalFeedbackReq.getScore(), goalFeedbackReq.getFeedback());

        return new GoalFeedbackResp(goal);
    }
}
