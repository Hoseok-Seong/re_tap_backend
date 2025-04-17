package hoselabs.future_letter.global.error.exception.jwt;

import hoselabs.future_letter.global.error.exception.BusinessException;
import hoselabs.future_letter.global.error.exception.ErrorCode;

public class AccessTokenExpiredException extends BusinessException {
    public AccessTokenExpiredException() {
        super(ErrorCode.ACCESS_TOKEN_EXPIRED);
    }
}