package hoselabs.future_letter.domain.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NaverUserInfo implements OauthUserInfo {

    private final String username;
    private final String profileImageUrl;
}
