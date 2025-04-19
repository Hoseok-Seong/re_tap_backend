package hoselabs.future_letter.domain.mypage.controller;

import hoselabs.future_letter.domain.mypage.dto.MyPageResp;
import hoselabs.future_letter.domain.mypage.service.MyPageService;
import hoselabs.future_letter.global.security.MyUserDetails;
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
        return ResponseEntity.ok().body(myPageService.getMyPage(userDetails.getUser()));
    }
}
