package hoselabs.future_letter.domain.letter.controller;

import hoselabs.future_letter.domain.letter.dto.LetterCreateReq;
import hoselabs.future_letter.domain.letter.dto.LetterCreateResp;
import hoselabs.future_letter.domain.letter.dto.LetterDetailResp;
import hoselabs.future_letter.domain.letter.dto.LetterListResp;
import hoselabs.future_letter.domain.letter.service.LetterService;
import hoselabs.future_letter.global.security.MyUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return ResponseEntity.ok().body(letterService.createLetter(myUserDetails, letterCreateReq));
    }

    @GetMapping("/letters")
    public ResponseEntity<LetterListResp> getLetters(@AuthenticationPrincipal final MyUserDetails userDetails) {
        return ResponseEntity.ok().body(letterService.getLetters(userDetails.getUser().getId()));
    }

    @GetMapping("/letters/{id}")
    public ResponseEntity<LetterDetailResp> getLetter(@AuthenticationPrincipal final MyUserDetails userDetails, @PathVariable final Long id) {
        return ResponseEntity.ok().body(letterService.getLetter(userDetails.getUser().getId(), id));
    }
}
