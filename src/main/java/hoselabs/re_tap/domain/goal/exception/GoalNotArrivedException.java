package hoselabs.re_tap.domain.goal.exception;

import hoselabs.re_tap.global.error.exception.BusinessException;
import hoselabs.re_tap.global.error.exception.ErrorCode;

public class GoalNotArrivedException extends BusinessException {
    public GoalNotArrivedException()  {
        super(ErrorCode.GOAL_NOT_ARRIVED);
    }
}
