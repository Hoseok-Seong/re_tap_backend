package hoselabs.future_letter.domain.home.controller;

import hoselabs.future_letter.domain.home.dto.HomeResp;
import hoselabs.future_letter.domain.home.service.HomeService;
import hoselabs.future_letter.global.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
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
    public HomeResp getHome(@AuthenticationPrincipal final MyUserDetails userDetails) {
        return homeService.getHome(userDetails.getUser().getId());
    }
}
