package hoselabs.future_letter.domain.letter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class LetterListResp {
    private List<LetterSummary> letters;

    @Getter
    @AllArgsConstructor
    public static class LetterSummary {
        private Long letterId;
        private String title;
        private LocalDate arrivalDate;
        private Boolean isLocked;
        private Boolean isArrived;
        private Boolean isRead;
    }
}