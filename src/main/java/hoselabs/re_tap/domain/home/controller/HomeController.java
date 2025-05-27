package hoselabs.re_tap.domain.home.controller;

import hoselabs.re_tap.domain.home.dto.HomeResp;
import hoselabs.re_tap.domain.home.service.HomeService;
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
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/home")
    public ResponseEntity<HomeResp> getHome(@AuthenticationPrincipal final MyUserDetails userDetails) {
        return ResponseEntity.ok().body(homeService.getHome(userDetails.getUser().getId()));
    }
}
