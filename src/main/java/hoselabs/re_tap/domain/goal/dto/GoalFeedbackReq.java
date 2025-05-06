package hoselabs.re_tap.domain.goal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoalFeedbackReq {
    @NotBlank(message = "아이디는 필수입니다.")
    private Long goalId;
    @NotBlank(message = "별점은 필수입니다.")
    private Integer score;
    @NotBlank(message = "피드백은 필수입니다.")
    private String feedback;
}
