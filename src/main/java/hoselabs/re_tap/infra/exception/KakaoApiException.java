package hoselabs.re_tap.infra.exception;

import hoselabs.re_tap.global.error.exception.ApiException;

public class KakaoApiException extends ApiException {
    public KakaoApiException(String msg) {
        super(msg);
    }
}
