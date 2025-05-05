package hoselabs.re_tap.domain.user.service;

import hoselabs.re_tap.domain.auth.dao.RefreshTokenRepository;
import hoselabs.re_tap.domain.goal.dao.GoalRepository;
import hoselabs.re_tap.domain.setup.MockTest;
import hoselabs.re_tap.domain.user.dao.UserRepository;
import hoselabs.re_tap.domain.user.dto.UpdateProfileReq;
import hoselabs.re_tap.domain.user.dto.UpdateProfileResp;
import hoselabs.re_tap.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

public class UserServiceTest extends MockTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private GoalRepository goalRepository;

    @Mock
    private UserRepository userRepository;

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

        // mocking: userRepository.findById(...) → user 반환
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        // when
        UpdateProfileResp resp = userService.updateProfile(user.getId(), req);

        // then
        assertThat(resp.getNickname()).isEqualTo("변경된 닉네임");
        assertThat(user.getNickname()).isEqualTo("변경된 닉네임");
    }

    @Test
    void 회원탈퇴_성공() {
        // mocking: userRepository.findById(...) → user 반환
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        // when
        userService.withdraw(user.getId());

        // then
        verify(refreshTokenRepository, times(1)).deleteByUserId(user.getId());
        verify(goalRepository, times(1)).deleteAllByUserId(user.getId());
        verify(userRepository, times(1)).delete(user);
    }
}
