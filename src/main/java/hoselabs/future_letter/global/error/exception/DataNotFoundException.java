package hoselabs.future_letter.global.error.exception;

public class DataNotFoundException extends BusinessException {
    public DataNotFoundException(String message) {
        super(message, ErrorCode.DATA_NOT_FOUND);
    }
}
