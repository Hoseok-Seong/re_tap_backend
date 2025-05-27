package hoselabs.re_tap.global.error.exception;

public class ApiException extends BusinessException {
    public ApiException(String message) {
        super(message, ErrorCode.API_EXCEPTION);
    }
}
