package hoselabs.future_letter.domain.auth.controller;

import hoselabs.future_letter.domain.auth.dto.OauthLoginReq;
import hoselabs.future_letter.domain.auth.dto.OauthLoginResp;
import hoselabs.future_letter.domain.auth.service.AuthService;
import hoselabs.future_letter.global.jwt.JwtProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/oauth-login")
    public ResponseEntity<OauthLoginResp> oauthLogin(@RequestBody @Valid final OauthLoginReq oauthLoginReq) {
        final OauthLoginResp oauthLoginResp = authService.oauthLogin(oauthLoginReq);

        return ResponseEntity.ok().header(JwtProvider.ACCESS_TOKEN_HEADER, oauthLoginResp.getAccessToken())
                .header(JwtProvider.REFRESH_TOKEN_HEADER, oauthLoginResp.getRefreshToken()).body(oauthLoginResp);
    }
}
