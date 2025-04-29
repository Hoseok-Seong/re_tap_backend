package hoselabs.future_letter.domain.letter.dto;

import hoselabs.future_letter.domain.letter.entity.LetterStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LetterDetailResp {
    private Long letterId;
    private String title;
    private String content;
    private LocalDate arrivalDate;
    private Boolean isLocked;
    private Boolean isArrived;
    private Boolean isRead;
    private LocalDateTime readAt;
    private LetterStatus status;
}

