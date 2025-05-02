package hoselabs.future_letter.domain.letter.dao;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hoselabs.future_letter.domain.letter.entity.Letter;
import hoselabs.future_letter.domain.letter.entity.LetterStatus;
import hoselabs.future_letter.domain.letter.entity.QLetter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class LetterRepositoryImpl implements LetterRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    QLetter letter = QLetter.letter;

    @Override
    public List<Letter> findAllByUserIdSorted(Long userId) {
        return queryFactory
                .selectFrom(letter)
                .where(letter.userId.eq(userId))
                .orderBy(
                        letter.createdAt.desc()
//                        new CaseBuilder()
//                                .when(letter.readAt.isNull()).then(0)
//                                .otherwise(1)
//                                .asc(), // 읽지 않은 편지가 위로
//                        letter.createdAt.desc(),
//                        letter.arrivalDate.desc()
                )
                .fetch();
    }

    @Override
    public int countUnreadAndArrived(Long userId) {
        Long count = queryFactory
                .select(letter.count())
                .from(letter)
                .where(
                        letter.userId.eq(userId),
                        letter.readAt.isNull(),
                        letter.status.in(LetterStatus.DELIVERED, LetterStatus.SCHEDULED)
                )
                .fetchOne();

        return Objects.requireNonNullElse(count, 0L).intValue();
    }

    @Override
    public List<Letter> findRecentLetters(Long userId, int limit) {
        return queryFactory
                .selectFrom(letter)
                .where(letter.userId.eq(userId))
                .orderBy(letter.createdAt.desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<Letter> findUpcomingLetters(Long userId, LocalDateTime from, LocalDateTime to, int limit) {
        return queryFactory
                .selectFrom(letter)
                .where(
                        letter.userId.eq(userId),
                        letter.arrivalDate.between(from, to),
                        letter.arrivalDate.gt(LocalDateTime.now()) // 아직 도착 안 한 편지
                )
                .orderBy(letter.arrivalDate.asc())
                .limit(limit)
                .fetch();
    }
}
