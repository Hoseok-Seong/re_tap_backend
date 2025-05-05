package hoselabs.re_tap.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import hoselabs.re_tap.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OauthLoginResp {
    private Long userId;
    private String username;
    private String accessToken;
    private String refreshToken;

    @JsonProperty("isNewUser")
    private boolean isNewUser;

    public OauthLoginResp(User user, String accessToken, String refreshToken, boolean isNewUser) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isNewUser = isNewUser;
    }
}
