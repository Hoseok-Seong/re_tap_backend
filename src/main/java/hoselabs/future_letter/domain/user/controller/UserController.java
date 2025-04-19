package hoselabs.future_letter.domain.user.controller;

import hoselabs.future_letter.domain.user.dto.UpdateProfileReq;
import hoselabs.future_letter.domain.user.dto.UpdateProfileResp;
import hoselabs.future_letter.domain.user.dto.WithdrawResp;
import hoselabs.future_letter.domain.user.service.UserService;
import hoselabs.future_letter.global.security.MyUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/profile")
    public ResponseEntity<UpdateProfileResp> updateProfile(
            @AuthenticationPrincipal final MyUserDetails userDetails,
            @RequestBody @Valid final UpdateProfileReq req
    ) {
        UpdateProfileResp resp = userService.updateProfile(userDetails.getUser(), req);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/user/withdraw")
    public ResponseEntity<WithdrawResp> withdraw(@AuthenticationPrincipal final MyUserDetails userDetails) {
        userService.withdraw(userDetails.getUser());
        return ResponseEntity.ok(new WithdrawResp("회원 탈퇴가 완료되었습니다."));
    }
}
