package hoselabs.future_letter.domain.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyPageResp {
    private Long id;
    private String username;
    private String provider;
    private String nickname;
    private String role;
    private String profileImageUrl;
}
