package hoselabs.re_tap.domain.auth.controller;

import hoselabs.re_tap.domain.auth.dto.OauthCheckReq;
import hoselabs.re_tap.domain.auth.dto.OauthCheckResp;
import hoselabs.re_tap.domain.auth.dto.OauthLoginReq;
import hoselabs.re_tap.domain.auth.dto.OauthLoginResp;
import hoselabs.re_tap.domain.auth.dto.OauthRegisterReq;
import hoselabs.re_tap.domain.auth.dto.RefreshTokenReq;
import hoselabs.re_tap.domain.auth.dto.RefreshTokenResp;
import hoselabs.re_tap.domain.auth.service.AuthService;
import hoselabs.re_tap.global.jwt.JwtProvider;
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

    @PostMapping("/check")
    public ResponseEntity<OauthCheckResp> check(@RequestBody @Valid final OauthCheckReq oauthCheckReq) {
        final OauthCheckResp oauthCheckResp = authService.check(oauthCheckReq);

        return ResponseEntity.ok(oauthCheckResp);
    }

    @PostMapping("/register")
    public ResponseEntity<OauthLoginResp> register(@RequestBody @Valid final OauthRegisterReq oauthRegisterReq) {
        final OauthLoginResp oauthLoginResp = authService.register(oauthRegisterReq);

        return ResponseEntity.ok().header(JwtProvider.ACCESS_TOKEN_HEADER, oauthLoginResp.getAccessToken())
                .header(JwtProvider.REFRESH_TOKEN_HEADER, oauthLoginResp.getRefreshToken()).body(oauthLoginResp);
    }

    @PostMapping("/oauth-login")
    public ResponseEntity<OauthLoginResp> oauthLogin(@RequestBody @Valid final OauthLoginReq oauthLoginReq) {
        final OauthLoginResp oauthLoginResp = authService.oauthLogin(oauthLoginReq);

        return ResponseEntity.ok().header(JwtProvider.ACCESS_TOKEN_HEADER, oauthLoginResp.getAccessToken())
                .header(JwtProvider.REFRESH_TOKEN_HEADER, oauthLoginResp.getRefreshToken()).body(oauthLoginResp);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResp> refresh(@RequestBody @Valid final RefreshTokenReq req) {
        RefreshTokenResp resp = authService.refresh(req.getRefreshToken());
        return ResponseEntity.ok(resp);
    }
}
