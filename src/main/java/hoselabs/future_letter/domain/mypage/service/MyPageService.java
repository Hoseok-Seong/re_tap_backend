package hoselabs.future_letter.domain.mypage.service;

import hoselabs.future_letter.domain.mypage.dto.MyPageResp;
import hoselabs.future_letter.domain.user.dao.UserRepository;
import hoselabs.future_letter.domain.user.entity.User;
import hoselabs.future_letter.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public MyPageResp getMyPage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return new MyPageResp(
                user.getId(),
                user.getUsername(),
                user.getProvider(),
                user.getNickname(),
                user.getRole().name(),
                user.getProfileImageUrl()
        );
    }
}
