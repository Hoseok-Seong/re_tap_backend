package hoselabs.re_tap.domain.goal.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoalFeedbackReq {
    @NotNull(message = "goalId는 필수입니다.")
    private Long goalId;
    @NotNull(message = "별점은 필수입니다.")
    @Min(value = 1, message = "별점은 1점 이상이어야 합니다.")
    private Integer score;
    @NotBlank(message = "피드백은 필수입니다.")
    private String feedback;
}
