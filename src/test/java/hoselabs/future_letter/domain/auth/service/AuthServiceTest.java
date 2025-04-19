package hoselabs.future_letter.domain.auth.service;

import hoselabs.future_letter.domain.auth.dao.RefreshTokenRepository;
import hoselabs.future_letter.domain.auth.dto.RefreshTokenResp;
import hoselabs.future_letter.domain.auth.entity.RefreshToken;
import hoselabs.future_letter.domain.setup.MockTest;
import hoselabs.future_letter.domain.user.dao.UserRepository;
import hoselabs.future_letter.domain.user.entity.User;
import hoselabs.future_letter.global.error.exception.jwt.InvalidRefreshTokenException;
import hoselabs.future_letter.global.error.exception.jwt.RefreshTokenExpiredException;
import hoselabs.future_letter.global.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

public class AuthServiceTest extends MockTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProvider jwtProvider;

    private User user;
    private RefreshToken refreshToken;
    private final String oldToken = "old-refresh-token";

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("testuser")
                .provider("kakao")
                .build();

        refreshToken = new RefreshToken(user.getId(), oldToken, LocalDateTime.now().plusDays(7));
    }

    @Test
    void 토큰_재발급_성공() {
        // given
        given(refreshTokenRepository.findByRefreshToken(oldToken)).willReturn(Optional.of(refreshToken));
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        String newAccessToken = "new-access-token";
        given(jwtProvider.createAccessToken(user)).willReturn(newAccessToken);
        String newRefreshToken = "new-refresh-token";
        given(jwtProvider.createRefreshToken(user)).willReturn(newRefreshToken);

        // when
        RefreshTokenResp resp = authService.refresh(oldToken);

        // then
        assertThat(resp.getAccessToken()).isEqualTo(newAccessToken);
        assertThat(resp.getRefreshToken()).isEqualTo(newRefreshToken);
    }

    @Test
    void 리프레시토큰_만료되었으면_예외() {
        // given
        RefreshToken expired = new RefreshToken(user.getId(), oldToken, LocalDateTime.now().minusSeconds(1));
        given(refreshTokenRepository.findByRefreshToken(oldToken)).willReturn(Optional.of(expired));

        // when & then
        assertThatThrownBy(() -> authService.refresh(oldToken))
                .isInstanceOf(RefreshTokenExpiredException.class)
                .hasMessageContaining("Refresh Token Expired");
    }

    @Test
    void 리프레시토큰_없으면_예외() {
        // given
        given(refreshTokenRepository.findByRefreshToken(oldToken)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> authService.refresh(oldToken))
                .isInstanceOf(InvalidRefreshTokenException.class)
                .hasMessageContaining("Invalid Refresh Token");
    }
}
