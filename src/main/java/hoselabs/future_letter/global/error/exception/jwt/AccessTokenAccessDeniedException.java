package hoselabs.future_letter.global.error.exception.jwt;

import hoselabs.future_letter.global.error.exception.BusinessException;
import hoselabs.future_letter.global.error.exception.ErrorCode;

public class AccessTokenAccessDeniedException extends BusinessException {
    public AccessTokenAccessDeniedException() {
        super(ErrorCode.INVALID_ACCESS_TOKEN_ACCESS_DENIED);
    }
}
