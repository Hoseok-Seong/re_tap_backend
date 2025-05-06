package hoselabs.re_tap.domain.goal.dto;

import hoselabs.re_tap.domain.goal.entity.Goal;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoalFeedbackResp {
    private Long goalId;
    private Integer score;
    private String feedback;

    public GoalFeedbackResp(Goal goal) {
        this.goalId = goal.getId();
        this.score = goal.getScore();
        this.feedback = goal.getFeedback();
    }
}
