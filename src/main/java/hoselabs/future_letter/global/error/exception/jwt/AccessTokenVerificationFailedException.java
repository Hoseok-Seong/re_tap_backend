package hoselabs.future_letter.global.error.exception.jwt;

import hoselabs.future_letter.global.error.exception.BusinessException;
import hoselabs.future_letter.global.error.exception.ErrorCode;

public class AccessTokenVerificationFailedException extends BusinessException {
    public AccessTokenVerificationFailedException() {
        super(ErrorCode.INVALID_ACCESS_TOKEN_VERIFICATION_FAILED);
    }
}
