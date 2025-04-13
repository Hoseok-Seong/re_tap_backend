package hoselabs.future_letter.domain.auth.service;

import hoselabs.future_letter.domain.auth.dao.RefreshTokenRepository;
import hoselabs.future_letter.domain.auth.dto.OauthLoginReq;
import hoselabs.future_letter.domain.auth.dto.OauthLoginResp;
import hoselabs.future_letter.domain.auth.dto.OauthUserInfo;
import hoselabs.future_letter.domain.auth.entity.RefreshToken;
import hoselabs.future_letter.domain.user.dao.UserFindDao;
import hoselabs.future_letter.domain.user.dao.UserRepository;
import hoselabs.future_letter.domain.user.entity.User;
import hoselabs.future_letter.global.jwt.JwtProvider;
import hoselabs.future_letter.infra.oauth.GoogleApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GoogleApiClient googleApiClient;
    private final UserFindDao userFindDao;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public OauthLoginResp oauthLogin(OauthLoginReq request) {
        OauthUserInfo userInfo = getUserInfo(request.getProvider(), request.getAccessToken());

        User user = userFindDao.findByUsernameAndProvider(userInfo.getUsername(), request.getProvider())
                .orElseGet(() -> registerNewUser(userInfo, request.getProvider()));

        String accessToken = JwtProvider.createAccessToken(user);
        String refreshToken = JwtProvider.createRefreshToken(user);

        saveRefreshToken(user, refreshToken);

        boolean isNewUser = (user.getNickname() == null);

        return new OauthLoginResp(user, accessToken, refreshToken, isNewUser);
    }

    private OauthUserInfo getUserInfo(String provider, String accessToken) {
        return switch (provider.toLowerCase()) {
//            case "kakao" -> kakaoApiClient.getUserInfo(accessToken);
            case "google" -> googleApiClient.getUserInfo(accessToken);
//            case "naver" -> naverApiClient.getUserInfo(accessToken);
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
}
