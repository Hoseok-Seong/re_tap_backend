package hoselabs.re_tap.domain.goal.dto;

import hoselabs.re_tap.domain.goal.entity.Goal;
import hoselabs.re_tap.domain.goal.entity.GoalStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoalCreateReq {
    private Long goalId;
    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
    private Boolean isLocked;
    private LocalDateTime arrivalDate;
    @NotNull(message = "isSend 값은 필수입니다.")
    private Boolean isSend;

    public Goal toEntity(Long userId, GoalStatus status) {
        return Goal.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .isLocked(isLocked)
                .arrivalDate(arrivalDate)
                .status(status)
                .build();
    }

    public void updateEntity(Goal goal, GoalStatus status) {
        goal.update(
                title,
                content,
                isLocked,
                arrivalDate,
                status
        );
    }
}
