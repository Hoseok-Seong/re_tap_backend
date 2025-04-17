package hoselabs.future_letter.domain.letter.exception;

import hoselabs.future_letter.global.error.exception.BusinessException;
import hoselabs.future_letter.global.error.exception.ErrorCode;

public class LetterNotArrivedException extends BusinessException {
    public LetterNotArrivedException(String message) {
        super(message, ErrorCode.LETTER_NOT_ARRIVED);
    }
}
