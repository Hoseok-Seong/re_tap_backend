package hoselabs.future_letter.infra.exception;

import hoselabs.future_letter.global.error.exception.ApiException;

public class NaverApiException extends ApiException {
    public NaverApiException(String msg) {
        super(msg);
    }
}