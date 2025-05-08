package hoselabs.re_tap.domain.user.exception;

import hoselabs.re_tap.global.error.exception.BusinessException;
import hoselabs.re_tap.global.error.exception.ErrorCode;

public class UserNicknameDuplicateException extends BusinessException {
    public UserNicknameDuplicateException() {
        super(ErrorCode.USER_NICKNAME_DUPLICATE);
    }
}
