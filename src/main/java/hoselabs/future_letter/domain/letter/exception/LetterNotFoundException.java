package hoselabs.future_letter.domain.letter.exception;

import hoselabs.future_letter.global.error.exception.DataNotFoundException;

public class LetterNotFoundException extends DataNotFoundException {
    public LetterNotFoundException(Long letterId) {
        super(letterId + " of letter is not found");
    }
}
