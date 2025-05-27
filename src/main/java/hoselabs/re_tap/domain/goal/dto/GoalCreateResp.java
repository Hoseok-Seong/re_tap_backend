package hoselabs.re_tap.domain.goal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoalCreateResp {
    private Long goalId;

    public GoalCreateResp(Long goalId) {
        this.goalId = goalId;
    }
}
