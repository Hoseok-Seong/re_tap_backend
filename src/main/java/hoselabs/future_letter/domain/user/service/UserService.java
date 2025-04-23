package hoselabs.future_letter.domain.user.service;

import hoselabs.future_letter.domain.auth.dao.RefreshTokenRepository;
import hoselabs.future_letter.domain.letter.dao.LetterRepository;
import hoselabs.future_letter.domain.user.dao.UserRepository;
import hoselabs.future_letter.domain.user.dto.UpdateProfileReq;
import hoselabs.future_letter.domain.user.dto.UpdateProfileResp;
import hoselabs.future_letter.domain.user.entity.User;
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
    public UpdateProfileResp updateProfile(User user, UpdateProfileReq req) {
        user.updateNickname(req.getNickname());

        return new UpdateProfileResp(user.getNickname());
    }

    @Transactional
    public void withdraw(User user) {
        // 1. Refresh Token 삭제
        refreshTokenRepository.deleteByUserId(user.getId());

        // 2. Letter 삭제 (userId 기준)
        letterRepository.deleteAllByUserId(user.getId());

        // 3. User 삭제
        userRepository.delete(user);
    }
}
