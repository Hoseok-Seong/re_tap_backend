package hoselabs.future_letter.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import hoselabs.future_letter.domain.user.entity.User;
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
