package hoselabs.re_tap.domain.goal.dto;

import hoselabs.re_tap.domain.goal.entity.GoalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoalListResp {
    private List<GoalSummary> goals;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoalSummary {
        private Long goalId;
        private String title;
        private String content;
        private LocalDate arrivalDate;
        private Boolean isLocked;
        private Boolean isArrived;
        private Boolean isRead;
        private LocalDate createdDate;
        private GoalStatus status;
    }
}
