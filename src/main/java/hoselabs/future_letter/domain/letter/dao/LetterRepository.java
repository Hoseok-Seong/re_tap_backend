package hoselabs.future_letter.domain.letter.dao;

import hoselabs.future_letter.domain.letter.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LetterRepository extends JpaRepository<Letter, Long>, LetterRepositoryCustom {
    Optional<Letter> findByIdAndUserId(Long letterId, Long userId);
}
