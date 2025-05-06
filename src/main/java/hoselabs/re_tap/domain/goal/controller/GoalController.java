package hoselabs.re_tap.domain.goal.controller;

import hoselabs.re_tap.domain.goal.dto.GoalCreateReq;
import hoselabs.re_tap.domain.goal.dto.GoalCreateResp;
import hoselabs.re_tap.domain.goal.dto.GoalDeleteReq;
import hoselabs.re_tap.domain.goal.dto.GoalDeleteResp;
import hoselabs.re_tap.domain.goal.dto.GoalDetailResp;
import hoselabs.re_tap.domain.goal.dto.GoalFeedbackReq;
import hoselabs.re_tap.domain.goal.dto.GoalFeedbackResp;
import hoselabs.re_tap.domain.goal.dto.GoalListResp;
import hoselabs.re_tap.domain.goal.service.GoalService;
import hoselabs.re_tap.global.security.MyUserDetails;
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
public class GoalController {
    private final GoalService goalService;

    @PostMapping("/goals")
    public ResponseEntity<GoalCreateResp> createGoal(@AuthenticationPrincipal final MyUserDetails myUserDetails,
                                                     @RequestBody @Valid final GoalCreateReq goalCreateReq) {
        return ResponseEntity.ok().body(goalService.createGoal(myUserDetails, goalCreateReq));
    }

    @GetMapping("/goals")
    public ResponseEntity<GoalListResp> getGoals(@AuthenticationPrincipal final MyUserDetails userDetails) {
        return ResponseEntity.ok().body(goalService.getGoals(userDetails.getUser().getId()));
    }

    @GetMapping("/goals/{id}")
    public ResponseEntity<GoalDetailResp> getGoal(@AuthenticationPrincipal final MyUserDetails userDetails, @PathVariable final Long id) {
        return ResponseEntity.ok().body(goalService.getGoal(userDetails.getUser().getId(), id));
    }

    @PostMapping("/goals/delete")
    public ResponseEntity<GoalDeleteResp> deleteGoals(@AuthenticationPrincipal final MyUserDetails myUserDetails,
                                                      @RequestBody @Valid final GoalDeleteReq goalDeleteReq) {
        return ResponseEntity.ok().body(goalService.deleteGoals(myUserDetails, goalDeleteReq));
    }

    @PostMapping("/goals/feedback")
    public ResponseEntity<GoalFeedbackResp> feedbackGoal(@AuthenticationPrincipal final MyUserDetails myUserDetails,
                                                         @RequestBody @Valid final GoalFeedbackReq goalFeedbackReq) {
        return ResponseEntity.ok().body(goalService.feedbackGoal(myUserDetails, goalFeedbackReq));
    }
}
