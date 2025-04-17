package hoselabs.future_letter.global.error.exception.jwt;

import hoselabs.future_letter.global.error.exception.BusinessException;
import hoselabs.future_letter.global.error.exception.ErrorCode;

public class InvalidRefreshTokenException extends BusinessException {
    public InvalidRefreshTokenException() {
        super(ErrorCode.INVALID_REFRESH_TOKEN);
    }
}
