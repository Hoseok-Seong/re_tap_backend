package hoselabs.future_letter.domain.letter.controller;

import hoselabs.future_letter.domain.letter.dto.LetterCreateReq;
import hoselabs.future_letter.domain.letter.dto.LetterCreateResp;
import hoselabs.future_letter.domain.letter.service.LetterService;
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
public class LetterController {
    private final LetterService letterService;

    @PostMapping("/letters")
    public ResponseEntity<LetterCreateResp> createLetter(@AuthenticationPrincipal final MyUserDetails myUserDetails,
                                                         @RequestBody @Valid final LetterCreateReq letterCreateReq) {
        final LetterCreateResp letterCreateResp = letterService.createLetter(myUserDetails, letterCreateReq);

        return ResponseEntity.ok().body(letterCreateResp);
    }
}
