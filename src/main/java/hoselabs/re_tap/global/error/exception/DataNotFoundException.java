package hoselabs.re_tap.global.error.exception;

public class DataNotFoundException extends BusinessException {
    public DataNotFoundException(String message) {
        super(message, ErrorCode.DATA_NOT_FOUND);
    }
}
