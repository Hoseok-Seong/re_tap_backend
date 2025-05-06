package hoselabs.re_tap.domain.user.service;

import hoselabs.re_tap.domain.auth.dao.RefreshTokenRepository;
import hoselabs.re_tap.domain.goal.dao.GoalRepository;
import hoselabs.re_tap.domain.user.dao.UserRepository;
import hoselabs.re_tap.domain.user.dto.FcmTokenReq;
import hoselabs.re_tap.domain.user.dto.FcmTokenResp;
import hoselabs.re_tap.domain.user.dto.UpdateProfileReq;
import hoselabs.re_tap.domain.user.dto.UpdateProfileResp;
import hoselabs.re_tap.domain.user.entity.User;
import hoselabs.re_tap.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    @Transactional
    public UpdateProfileResp updateProfile(Long userId, UpdateProfileReq req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.updateNickname(req.getNickname());

        return new UpdateProfileResp(user.getNickname());
    }

    @Transactional
    public void withdraw(Long userId) {
        // 1. Refresh Token 삭제
        refreshTokenRepository.deleteByUserId(userId);

        // 2. goal 삭제 (userId 기준)
        goalRepository.deleteAllByUserId(userId);

        // 3. User 삭제
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(user);
    }

    @Transactional
    public FcmTokenResp updateFcmToken(Long userId, FcmTokenReq req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.updateFcmToken(req.getFcmToken());

        return new FcmTokenResp(user.getId());
    }
}
