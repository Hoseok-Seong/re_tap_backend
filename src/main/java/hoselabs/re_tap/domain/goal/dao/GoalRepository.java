package hoselabs.re_tap.domain.goal.dao;

import hoselabs.re_tap.domain.goal.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long>, GoalRepositoryCustom {
    Optional<Goal> findByIdAndUserId(Long goalId, Long userId);
    void deleteAllByUserId(Long userId);
}
