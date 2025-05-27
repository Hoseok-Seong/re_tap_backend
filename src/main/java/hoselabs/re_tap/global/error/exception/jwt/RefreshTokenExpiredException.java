package hoselabs.re_tap.global.error.exception.jwt;

import hoselabs.re_tap.global.error.exception.BusinessException;
import hoselabs.re_tap.global.error.exception.ErrorCode;

public class RefreshTokenExpiredException extends BusinessException {
    public RefreshTokenExpiredException() {
        super(ErrorCode.REFRESH_TOKEN_EXPIRED);
    }
}