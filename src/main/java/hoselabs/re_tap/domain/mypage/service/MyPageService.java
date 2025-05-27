package hoselabs.re_tap.domain.mypage.service;

import hoselabs.re_tap.domain.mypage.dto.MyPageResp;
import hoselabs.re_tap.domain.user.dao.UserRepository;
import hoselabs.re_tap.domain.user.entity.User;
import hoselabs.re_tap.domain.user.exception.UserNotFoundException;
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
