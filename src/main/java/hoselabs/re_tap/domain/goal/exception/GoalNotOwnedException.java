package hoselabs.re_tap.domain.goal.exception;

import hoselabs.re_tap.global.error.exception.BusinessException;
import hoselabs.re_tap.global.error.exception.ErrorCode;

public class GoalNotOwnedException extends BusinessException {
    public GoalNotOwnedException() {
        super(ErrorCode.GOAL_NOT_OWNED);
    }
}
