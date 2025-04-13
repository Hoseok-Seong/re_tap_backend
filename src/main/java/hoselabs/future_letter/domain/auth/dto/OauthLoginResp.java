package hoselabs.future_letter.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OauthLoginResp {
    private Long userId;
    private String accessToken;
    private String refreshToken;
}
