package hoselabs.future_letter.global.error.exception;

public class UserDetailsException extends BusinessException {
    public UserDetailsException() {
        super(ErrorCode.USER_DETAILS_NULL);
    }
}
