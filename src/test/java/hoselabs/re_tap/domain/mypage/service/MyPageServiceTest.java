package hoselabs.re_tap.domain.mypage.service;

import hoselabs.re_tap.domain.mypage.dto.MyPageResp;
import hoselabs.re_tap.domain.setup.MockTest;
import hoselabs.re_tap.domain.user.entity.User;
import hoselabs.re_tap.global.security.MyUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.*;

public class MyPageServiceTest extends MockTest {

    @InjectMocks
    private MyPageService myPageService;

    private User user;
    private MyUserDetails userDetails;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("user1")
                .provider("kakao")
                .profileImageUrl("https://example.com/image.png")
                .build();

        userDetails = new MyUserDetails(user);

        user.updateNickname("닉네임1");
    }

    @Test
    void 마이페이지_조회_성공() {
        // when
        MyPageResp result = myPageService.getMyPage(user.getId());

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUsername()).isEqualTo("user1");
        assertThat(result.getProvider()).isEqualTo("kakao");
        assertThat(result.getNickname()).isEqualTo("닉네임1");
        assertThat(result.getRole()).isEqualTo("USER");
        assertThat(result.getProfileImageUrl()).isEqualTo("https://example.com/image.png");
    }
}
