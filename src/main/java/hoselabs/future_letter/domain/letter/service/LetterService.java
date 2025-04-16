package hoselabs.future_letter.domain.letter.service;

import hoselabs.future_letter.domain.letter.dao.LetterRepository;
import hoselabs.future_letter.domain.letter.dto.LetterCreateReq;
import hoselabs.future_letter.domain.letter.dto.LetterCreateResp;
import hoselabs.future_letter.domain.letter.entity.Letter;
import hoselabs.future_letter.domain.letter.entity.LetterStatus;
import hoselabs.future_letter.global.error.exception.UserDetailsException;
import hoselabs.future_letter.global.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        final Letter letter = Letter.builder()
                .userId(myUserDetails.getUser().getId())
                .title(letterCreateReq.getTitle())
                .content(letterCreateReq.getContent())
                .isLocked(letterCreateReq.getIsLocked())
                .arrivalDate(letterCreateReq.getArrivalDate())
                .status(status)
                .build();

        letterRepository.save(letter);

        return new LetterCreateResp(letter.getId());
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
}
