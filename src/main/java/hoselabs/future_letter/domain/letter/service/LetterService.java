package hoselabs.future_letter.domain.letter.service;

import hoselabs.future_letter.domain.letter.dao.LetterRepository;
import hoselabs.future_letter.domain.letter.dto.LetterCreateReq;
import hoselabs.future_letter.domain.letter.dto.LetterCreateResp;
import hoselabs.future_letter.domain.letter.dto.LetterDetailResp;
import hoselabs.future_letter.domain.letter.dto.LetterListResp;
import hoselabs.future_letter.domain.letter.entity.Letter;
import hoselabs.future_letter.domain.letter.entity.LetterStatus;
import hoselabs.future_letter.domain.letter.exception.LetterNotArrivedException;
import hoselabs.future_letter.domain.letter.exception.LetterNotFoundException;
import hoselabs.future_letter.domain.user.exception.UserNotOwnerException;
import hoselabs.future_letter.global.error.exception.UserDetailsException;
import hoselabs.future_letter.global.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;

    @Transactional
    public LetterCreateResp createLetter(final MyUserDetails myUserDetails, final LetterCreateReq letterCreateReq) {
        if (myUserDetails == null) {
            throw new UserDetailsException();
        }

        LetterStatus status = determineStatus(letterCreateReq.getIsSend(), letterCreateReq.getIsLocked());

        Long userId = myUserDetails.getUser().getId();

        // 편지 ID가 null이 아니면 수정
        if (letterCreateReq.getLetterId() != null) {
            Letter letter = letterRepository.findById(letterCreateReq.getLetterId())
                    .orElseThrow(() -> new LetterNotFoundException(letterCreateReq.getLetterId()));

            if (!letter.getUserId().equals(userId)) {
                throw new UserNotOwnerException();
            }

            letterCreateReq.updateEntity(letter, status);
            return new LetterCreateResp(letter.getId()); // 수정 후 id 반환
        }

        // 새 편지 생성
        Letter saved = letterRepository.save(letterCreateReq.toEntity(userId, status));
        return new LetterCreateResp(saved.getId());
    }

    private LetterStatus determineStatus(Boolean isSend, Boolean isLocked) {
        if (!isSend) {
            return LetterStatus.DRAFT;
        }
        if (Boolean.TRUE.equals(isLocked)) {
            return LetterStatus.SCHEDULED;
        }
        return LetterStatus.DELIVERED;
    }

    @Transactional(readOnly = true)
    public LetterListResp getLetters(Long userId) {
        List<Letter> letters = letterRepository.findAllByUserIdSorted(userId);

        List<LetterListResp.LetterSummary> summaries = letters.stream()
                .map(letter -> new LetterListResp.LetterSummary(
                        letter.getId(),
                        letter.getTitle(),
                        letter.getContent(),
                        letter.getArrivalDate() != null ? letter.getArrivalDate().toLocalDate() : null,
                        letter.getIsLocked(),
                        letter.getArrivalDate() != null && letter.getArrivalDate().isBefore(LocalDateTime.now()),
                        letter.getReadAt() != null,
                        letter.getCreatedAt().toLocalDate(),
                        letter.getStatus()
                ))
                .toList();

        return new LetterListResp(summaries);
    }

    @Transactional
    public LetterDetailResp getLetter(Long userId, Long letterId) {
        Letter letter = letterRepository.findByIdAndUserId(letterId, userId)
                .orElseThrow(() -> new LetterNotFoundException(letterId));

        if (letter.getStatus() == LetterStatus.SCHEDULED && !letter.isArrived()) {
            throw new LetterNotArrivedException();
        }

        if (!letter.isRead()) {
            letter.markAsRead();
        }

        return new LetterDetailResp(
                letter.getId(),
                letter.getTitle(),
                letter.getContent(),
                letter.getArrivalDate() != null ? letter.getArrivalDate().toLocalDate() : null,
                letter.getIsLocked(),
                letter.isArrived(),
                letter.isRead(),
                letter.getReadAt(),
                letter.getStatus()
        );
    }

}
