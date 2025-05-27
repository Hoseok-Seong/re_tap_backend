package hoselabs.re_tap.domain.goal.dao;

import hoselabs.re_tap.domain.goal.entity.Goal;

import java.time.LocalDateTime;
import java.util.List;

public interface GoalRepositoryCustom {
    List<Goal> findAllByUserIdSorted(Long userId);
    int countUnreadAndArrived(Long userId);
    List<Goal> findRecentGoals(Long userId, int limit);
    List<Goal> findUpcomingGoals(Long userId, LocalDateTime from, LocalDateTime to, int limit);
}
