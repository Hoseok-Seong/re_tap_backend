package hoselabs.re_tap.global.error.exception.jwt;

import hoselabs.re_tap.global.error.exception.BusinessException;
import hoselabs.re_tap.global.error.exception.ErrorCode;

public class InvalidRefreshTokenException extends BusinessException {
    public InvalidRefreshTokenException() {
        super(ErrorCode.INVALID_REFRESH_TOKEN);
    }
}
