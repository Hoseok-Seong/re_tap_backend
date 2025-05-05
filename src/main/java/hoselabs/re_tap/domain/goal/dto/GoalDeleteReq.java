package hoselabs.re_tap.domain.goal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoalDeleteReq {
    private List<Long> goalIds;
}
