package hoselabs.re_tap.domain.mypage.controller;

import hoselabs.re_tap.domain.mypage.dto.MyPageResp;
import hoselabs.re_tap.domain.mypage.service.MyPageService;
import hoselabs.re_tap.global.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/my-page")
    public ResponseEntity<MyPageResp> getMyPage(@AuthenticationPrincipal final MyUserDetails userDetails) {
        return ResponseEntity.ok().body(myPageService.getMyPage(userDetails.getUser().getId()));
    }
}
