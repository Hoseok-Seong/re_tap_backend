package hoselabs.re_tap.domain.user.exception;

import hoselabs.re_tap.global.error.exception.BusinessException;
import hoselabs.re_tap.global.error.exception.ErrorCode;

public class UserNotOwnerException extends BusinessException {
    public UserNotOwnerException() {
        super(ErrorCode.USER_NOT_OWNER);
    }
}
