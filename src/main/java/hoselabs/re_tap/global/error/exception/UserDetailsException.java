package hoselabs.re_tap.global.error.exception;

public class UserDetailsException extends BusinessException {
    public UserDetailsException() {
        super(ErrorCode.USER_DETAILS_NULL);
    }
}
