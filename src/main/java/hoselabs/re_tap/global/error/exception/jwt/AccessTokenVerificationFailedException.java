package hoselabs.re_tap.global.error.exception.jwt;

import hoselabs.re_tap.global.error.exception.BusinessException;
import hoselabs.re_tap.global.error.exception.ErrorCode;

public class AccessTokenVerificationFailedException extends BusinessException {
    public AccessTokenVerificationFailedException() {
        super(ErrorCode.INVALID_ACCESS_TOKEN_VERIFICATION_FAILED);
    }
}
