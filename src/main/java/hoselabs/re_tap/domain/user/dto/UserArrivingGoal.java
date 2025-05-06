package hoselabs.re_tap.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserArrivingGoal {
    private Long userId;
    private String fcmToken;
    private Long count;
}
