package hoselabs.future_letter.domain.letter.dao;

import hoselabs.future_letter.domain.letter.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, Long> {
}
