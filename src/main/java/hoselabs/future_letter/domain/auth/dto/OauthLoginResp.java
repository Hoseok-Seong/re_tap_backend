package hoselabs.future_letter.domain.auth.dto;

import hoselabs.future_letter.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OauthLoginResp {
    private Long userId;
    private String username;
    private String accessToken;
    private String refreshToken;
    private boolean isNewUser;

    public OauthLoginResp(User user, String accessToken, String refreshToken, boolean isNewUser) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isNewUser = isNewUser;
    }
}
