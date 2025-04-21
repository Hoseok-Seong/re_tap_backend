package hoselabs.future_letter.domain.auth.service;

import hoselabs.future_letter.domain.auth.dao.RefreshTokenRepository;
import hoselabs.future_letter.domain.auth.dto.OauthCheckReq;
import hoselabs.future_letter.domain.auth.dto.OauthCheckResp;
import hoselabs.future_letter.domain.auth.dto.OauthLoginReq;
import hoselabs.future_letter.domain.auth.dto.OauthLoginResp;
import hoselabs.future_letter.domain.auth.dto.OauthRegisterReq;
import hoselabs.future_letter.domain.auth.dto.OauthUserInfo;
import hoselabs.future_letter.domain.auth.dto.RefreshTokenResp;
import hoselabs.future_letter.domain.auth.entity.RefreshToken;
import hoselabs.future_letter.domain.user.dao.UserFindDao;
import hoselabs.future_letter.domain.user.dao.UserRepository;
import hoselabs.future_letter.domain.user.entity.User;
import hoselabs.future_letter.domain.user.exception.UserAlreadyExistsException;
import hoselabs.future_letter.domain.user.exception.UserNotFoundException;
import hoselabs.future_letter.global.error.exception.jwt.InvalidRefreshTokenException;
import hoselabs.future_letter.global.error.exception.jwt.RefreshTokenExpiredException;
import hoselabs.future_letter.global.jwt.JwtProvider;
import hoselabs.future_letter.infra.oauth.GoogleApiClient;
import hoselabs.future_letter.infra.oauth.KakaoApiClient;
import hoselabs.future_letter.infra.oauth.NaverApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GoogleApiClient googleApiClient;
    private final KakaoApiClient kakaoApiClient;
    private final NaverApiClient naverApiClient;
    private final UserFindDao userFindDao;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public OauthCheckResp check(OauthCheckReq request) {
        OauthUserInfo userInfo = getUserInfo(request.getProvider(), request.getAccessToken());

        Optional<User> userOpt = userFindDao.findByUsernameAndProvider(userInfo.getUsername(), request.getProvider());

        boolean isNewUser = userOpt.isEmpty();

        return new OauthCheckResp(isNewUser);
    }

    @Transactional
    public OauthLoginResp register(OauthRegisterReq request) {
        OauthUserInfo userInfo = getUserInfo(request.getProvider(), request.getAccessToken());

        if (userRepository.existsByUsernameAndProvider(userInfo.getUsername(), request.getProvider())) {
            throw new UserAlreadyExistsException();
        }

        User user = registerNewUser(userInfo, request.getProvider());

        String accessToken = jwtProvider.createAccessToken(user);
        String refreshToken = jwtProvider.createRefreshToken(user);

        saveRefreshToken(user, refreshToken);

        return new OauthLoginResp(user, accessToken, refreshToken, true);
    }

    @Transactional
    public OauthLoginResp oauthLogin(OauthLoginReq request) {
        OauthUserInfo userInfo = getUserInfo(request.getProvider(), request.getAccessToken());

        User user = userFindDao.findByUsernameAndProvider(userInfo.getUsername(), request.getProvider())
                .orElseThrow(() -> new UserNotFoundException(userInfo.getUsername(), request.getProvider()));

        String accessToken = jwtProvider.createAccessToken(user);
        String refreshToken = jwtProvider.createRefreshToken(user);

        saveRefreshToken(user, refreshToken);

        return new OauthLoginResp(user, accessToken, refreshToken, false);
    }

    private OauthUserInfo getUserInfo(String provider, String accessToken) {
        return switch (provider.toLowerCase()) {
            case "kakao" -> kakaoApiClient.getUserInfo(accessToken);
            case "google" -> googleApiClient.getUserInfo(accessToken);
            case "naver" -> naverApiClient.getUserInfo(accessToken);
            default -> throw new RuntimeException("지원하지 않는 provider");
        };
    }

    private User registerNewUser(OauthUserInfo userInfo, String provider) {
        User user = User.builder()
                .username(userInfo.getUsername())
                .provider(provider)
                .profileImageUrl(userInfo.getProfileImageUrl())
                .build();

        return userRepository.save(user);
    }

    private void saveRefreshToken(User user, String refreshToken) {
        refreshTokenRepository.deleteByUserId(user.getId()); // 기존 토큰 삭제

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .userId(user.getId())
                        .refreshToken(refreshToken)
                        .expiresAt(LocalDateTime.now().plusDays(7))
                        .build()
        );
    }

    @Transactional
    public RefreshTokenResp refresh(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(InvalidRefreshTokenException::new);

        if (token.isExpired()) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenExpiredException();
        }

        User user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> new UserNotFoundException(token.getUserId()));

        String newAccessToken = jwtProvider.createAccessToken(user);
        String newRefreshToken = jwtProvider.createRefreshToken(user);

        saveRefreshToken(user, newRefreshToken);

        return new RefreshTokenResp(newAccessToken, newRefreshToken);
    }
}
