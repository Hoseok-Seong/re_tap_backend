package hoselabs.future_letter.domain.user.service;

import hoselabs.future_letter.domain.setup.MockTest;
import hoselabs.future_letter.domain.user.dto.UpdateProfileReq;
import hoselabs.future_letter.domain.user.dto.UpdateProfileResp;
import hoselabs.future_letter.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.*;

public class UserServiceTest extends MockTest {

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("회원1")
                .provider("kakao")
                .profileImageUrl("https://example.com/image.png")
                .build();
    }

    @Test
    void 프로필_수정_성공() {
        // given
        UpdateProfileReq req = new UpdateProfileReq("변경된 닉네임");
        user.updateNickname(req.getNickname());

        // when
        UpdateProfileResp resp = userService.updateProfile(user, req);

        // then
        assertThat(resp.getNickname()).isEqualTo("변경된 닉네임");
        assertThat(user.getNickname()).isEqualTo("변경된 닉네임");
    }
}
