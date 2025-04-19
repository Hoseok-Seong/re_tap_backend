package hoselabs.future_letter.domain.user.service;

import hoselabs.future_letter.domain.user.dto.UpdateProfileReq;
import hoselabs.future_letter.domain.user.dto.UpdateProfileResp;
import hoselabs.future_letter.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Transactional
    public UpdateProfileResp updateProfile(User user, UpdateProfileReq req) {
        user.updateNickname(req.getNickname());
        return new UpdateProfileResp(user.getNickname());
    }
}
