package hoselabs.future_letter.domain.letter.dao;

import hoselabs.future_letter.domain.letter.entity.Letter;

import java.util.List;

public interface LetterRepositoryCustom {
    List<Letter> findAllByUserIdSorted(Long userId);
}
