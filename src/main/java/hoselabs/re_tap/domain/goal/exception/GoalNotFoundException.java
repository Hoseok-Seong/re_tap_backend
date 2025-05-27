package hoselabs.re_tap.domain.goal.exception;

import hoselabs.re_tap.global.error.exception.DataNotFoundException;

public class GoalNotFoundException extends DataNotFoundException {
    public GoalNotFoundException(Long goalId) {
        super(goalId + " of goal is not found");
    }
}
