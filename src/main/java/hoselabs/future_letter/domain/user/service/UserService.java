package hoselabs.future_letter.domain.user.service;

import hoselabs.future_letter.domain.auth.dao.RefreshTokenRepository;
import hoselabs.future_letter.domain.letter.dao.LetterRepository;
import hoselabs.future_letter.domain.user.dao.UserRepository;
import hoselabs.future_letter.domain.user.dto.UpdateProfileReq;
import hoselabs.future_letter.domain.user.dto.UpdateProfileResp;
import hoselabs.future_letter.domain.user.entity.User;
import hoselabs.future_letter.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final LetterRepository letterRepository;
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

        // 2. Letter 삭제 (userId 기준)
        letterRepository.deleteAllByUserId(userId);

        // 3. User 삭제
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(user);
    }
}
