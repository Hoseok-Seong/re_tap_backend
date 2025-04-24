package hoselabs.future_letter.domain.user.exception;

import hoselabs.future_letter.global.error.exception.BusinessException;
import hoselabs.future_letter.global.error.exception.ErrorCode;

public class UserNotOwnerException extends BusinessException {
    public UserNotOwnerException() {
        super(ErrorCode.USER_NOT_OWNER);
    }
}
