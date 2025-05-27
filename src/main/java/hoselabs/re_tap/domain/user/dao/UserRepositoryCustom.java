package hoselabs.re_tap.domain.user.dao;

import hoselabs.re_tap.domain.user.dto.UserArrivingGoal;

import java.util.List;

public interface UserRepositoryCustom {
    List<UserArrivingGoal> findAllWithArrivingGoalsToday();
}
