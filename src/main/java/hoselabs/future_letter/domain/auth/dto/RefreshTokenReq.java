package hoselabs.future_letter.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenReq {
    @NotBlank(message = "refresh-token은 필수입니다")
    private String refreshToken;
}

