package hoselabs.future_letter.infra.exception;

import hoselabs.future_letter.global.error.exception.ApiException;

public class KakaoApiException extends ApiException {
    public KakaoApiException(String msg) {
        super(msg);
    }
}
