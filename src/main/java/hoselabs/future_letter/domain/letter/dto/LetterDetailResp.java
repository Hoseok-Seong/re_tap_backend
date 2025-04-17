package hoselabs.future_letter.domain.letter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
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
    private Boolean editable;
}

