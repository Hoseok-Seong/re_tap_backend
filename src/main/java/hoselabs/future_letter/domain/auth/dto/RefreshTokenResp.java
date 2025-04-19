package hoselabs.future_letter.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenResp {
    private String accessToken;
    private String refreshToken;
}
