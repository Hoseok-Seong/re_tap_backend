package hoselabs.future_letter.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileReq {
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;
}
