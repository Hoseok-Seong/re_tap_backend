package hoselabs.future_letter.domain.letter.exception;

import hoselabs.future_letter.global.error.exception.BusinessException;
import hoselabs.future_letter.global.error.exception.ErrorCode;

public class LetterNotOwnedException extends BusinessException {
    public LetterNotOwnedException() {
        super(ErrorCode.LETTER_NOT_OWNED);
    }
}
