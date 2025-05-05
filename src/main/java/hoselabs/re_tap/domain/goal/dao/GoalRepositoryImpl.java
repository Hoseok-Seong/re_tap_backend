package hoselabs.re_tap.domain.goal.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hoselabs.re_tap.domain.goal.entity.Goal;
import hoselabs.re_tap.domain.goal.entity.GoalStatus;
import hoselabs.re_tap.domain.goal.entity.QGoal;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class GoalRepositoryImpl implements GoalRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    QGoal goal = QGoal.goal;

    @Override
    public List<Goal> findAllByUserIdSorted(Long userId) {
        return queryFactory
                .selectFrom(goal)
                .where(goal.userId.eq(userId))
                .orderBy(
                        goal.createdAt.desc()
//                        new CaseBuilder()
//                                .when(goal.readAt.isNull()).then(0)
//                                .otherwise(1)
//                                .asc(),
//                        goal.createdAt.desc(),
//                        goal.arrivalDate.desc()
                )
                .fetch();
    }

    @Override
    public int countUnreadAndArrived(Long userId) {
        Long count = queryFactory
                .select(goal.count())
                .from(goal)
                .where(
                        goal.userId.eq(userId),
                        goal.readAt.isNull(),
                        goal.status.in(GoalStatus.DELIVERED, GoalStatus.SCHEDULED)
                )
                .fetchOne();

        return Objects.requireNonNullElse(count, 0L).intValue();
    }

    @Override
    public List<Goal> findRecentGoals(Long userId, int limit) {
        return queryFactory
                .selectFrom(goal)
                .where(goal.userId.eq(userId))
                .orderBy(goal.createdAt.desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<Goal> findUpcomingGoals(Long userId, LocalDateTime from, LocalDateTime to, int limit) {
        return queryFactory
                .selectFrom(goal)
                .where(
                        goal.userId.eq(userId),
                        goal.arrivalDate.between(from, to),
                        goal.arrivalDate.gt(LocalDateTime.now())
                )
                .orderBy(goal.arrivalDate.asc())
                .limit(limit)
                .fetch();
    }
}
