package hoselabs.future_letter.domain.letter.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hoselabs.future_letter.domain.letter.entity.Letter;
import hoselabs.future_letter.domain.letter.entity.QLetter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LetterRepositoryImpl implements LetterRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Letter> findAllByUserIdSorted(Long userId) {
        QLetter letter = QLetter.letter;

        return queryFactory
                .selectFrom(letter)
                .where(letter.userId.eq(userId))
                .orderBy(
                        letter.readAt.isNull().desc(),
                        letter.isLocked.asc(),
                        letter.arrivalDate.desc()
                )
                .fetch();
    }
}
