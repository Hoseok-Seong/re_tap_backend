package hoselabs.future_letter.domain.letter.dao;

import hoselabs.future_letter.domain.letter.entity.Letter;

import java.time.LocalDateTime;
import java.util.List;

public interface LetterRepositoryCustom {
    List<Letter> findAllByUserIdSorted(Long userId);
    int countUnreadAndArrived(Long userId);
    List<Letter> findRecentLetters(Long userId, int limit);
    List<Letter> findUpcomingLetters(Long userId, LocalDateTime from, LocalDateTime to, int limit);
}
