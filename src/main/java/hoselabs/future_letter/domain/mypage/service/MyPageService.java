package hoselabs.future_letter.domain.mypage.service;

import hoselabs.future_letter.domain.mypage.dto.MyPageResp;
import hoselabs.future_letter.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    @Transactional(readOnly = true)
    public MyPageResp getMyPage(User user) {
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
