package hoselabs.re_tap.infra.exception;

import hoselabs.re_tap.global.error.exception.ApiException;

public class NaverApiException extends ApiException {
    public NaverApiException(String msg) {
        super(msg);
    }
}