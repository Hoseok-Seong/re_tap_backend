package hoselabs.future_letter.global.error.exception;

public class InvalidInputException extends BusinessException {
    public InvalidInputException(String value) {
        super(value, ErrorCode.INVALID_INPUT_VALUE);
    }

    public InvalidInputException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
