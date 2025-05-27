package hoselabs.re_tap.domain.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageResp {
    private Long id;
    private String username;
    private String provider;
    private String nickname;
    private String role;
    private String profileImageUrl;
}
