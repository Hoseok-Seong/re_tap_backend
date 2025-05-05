package hoselabs.re_tap.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OauthCheckResp {
    @JsonProperty("isNewUser")
    private boolean isNewUser;

    public OauthCheckResp(boolean isNewUser) {
        this.isNewUser = isNewUser;
    }
}
