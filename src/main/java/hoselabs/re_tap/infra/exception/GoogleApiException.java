package hoselabs.re_tap.infra.exception;

import hoselabs.re_tap.global.error.exception.ApiException;

public class GoogleApiException extends ApiException {
    public GoogleApiException(String msg) {
        super(msg);
    }
}
