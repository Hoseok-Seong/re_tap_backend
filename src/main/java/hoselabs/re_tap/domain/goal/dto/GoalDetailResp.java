package hoselabs.re_tap.domain.goal.dto;

import hoselabs.re_tap.domain.goal.entity.GoalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoalDetailResp {
    private Long goalId;
    private String title;
    private String content;
    private LocalDate arrivalDate;
    private Boolean isLocked;
    private Boolean isArrived;
    private Boolean isRead;
    private LocalDateTime readAt;
    private GoalStatus status;
    private Integer score;
    private String feedback;
}
