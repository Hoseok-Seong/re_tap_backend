package hoselabs.re_tap.global.fcm;

import hoselabs.re_tap.domain.user.dao.UserRepository;
import hoselabs.re_tap.domain.user.dto.UserArrivingGoal;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoalScheduler {

    private final FcmService fcmService;
    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 9 * * ?")
    public void notifyUsersOfArrivedGoals() {
        List<UserArrivingGoal> users = userRepository.findAllWithArrivingGoalsToday();

        for (UserArrivingGoal user : users) {
            try {
                fcmService.sendMessage(
                        user.getFcmToken(),
                        "🎯 오늘 도착한 목표!",
                        "오늘 도착한 목표가 " + user.getCount() + "개 있어요."
                );
            } catch (Exception e) {
                log.error("FCM 전송 실패", e);
            }
        }
    }
}
