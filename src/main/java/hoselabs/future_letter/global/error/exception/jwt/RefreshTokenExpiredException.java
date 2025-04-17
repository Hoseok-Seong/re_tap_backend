package hoselabs.future_letter.global.error.exception.jwt;

import hoselabs.future_letter.global.error.exception.BusinessException;
import hoselabs.future_letter.global.error.exception.ErrorCode;

public class RefreshTokenExpiredException extends BusinessException {
    public RefreshTokenExpiredException() {
        super(ErrorCode.REFRESH_TOKEN_EXPIRED);
    }
}