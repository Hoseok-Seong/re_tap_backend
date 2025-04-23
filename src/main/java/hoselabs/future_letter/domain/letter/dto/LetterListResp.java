package hoselabs.future_letter.domain.letter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LetterListResp {
    private List<LetterSummary> letters;

    @Getter
    @NoArgsConstructor
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