package hoselabs.future_letter.domain.auth.service;

import hoselabs.future_letter.domain.auth.dto.OauthLoginReq;
import hoselabs.future_letter.domain.auth.dto.OauthLoginResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    @Transactional
    public OauthLoginResp oauthLogin(OauthLoginReq oauthLoginReq) {
        OauthLoginResp oauthLoginResp = new OauthLoginResp();
        return oauthLoginResp;
    }
}
