package hoselabs.re_tap.global.error.exception.jwt;

import hoselabs.re_tap.global.error.exception.BusinessException;
import hoselabs.re_tap.global.error.exception.ErrorCode;

public class AccessTokenAccessDeniedException extends BusinessException {
    public AccessTokenAccessDeniedException() {
        super(ErrorCode.INVALID_ACCESS_TOKEN_ACCESS_DENIED);
    }
}
