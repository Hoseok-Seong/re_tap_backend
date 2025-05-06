package hoselabs.re_tap.domain.user.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hoselabs.re_tap.domain.goal.entity.GoalStatus;
import hoselabs.re_tap.domain.goal.entity.QGoal;
import hoselabs.re_tap.domain.user.dto.UserArrivingGoal;
import hoselabs.re_tap.domain.user.entity.QUser;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    QUser user = QUser.user;
    QGoal goal = QGoal.goal;

    public List<UserArrivingGoal> findAllWithArrivingGoalsToday() {
        return queryFactory
                .select(Projections.constructor(
                        UserArrivingGoal.class,
                        user.id,
                        user.fcmToken,
                        goal.id.count()
                ))
                .from(user, goal)
                .where(
                        user.id.eq(goal.userId),
                        goal.status.eq(GoalStatus.SCHEDULED),
                        goal.arrivalDate.year().eq(LocalDate.now().getYear()),
                        goal.arrivalDate.month().eq(LocalDate.now().getMonthValue()),
                        goal.arrivalDate.dayOfMonth().eq(LocalDate.now().getDayOfMonth())
                )
                .groupBy(user.id, user.fcmToken)
                .fetch();
    }
}
