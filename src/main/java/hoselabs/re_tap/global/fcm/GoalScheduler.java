package hoselabs.re_tap.global.fcm;

import hoselabs.re_tap.domain.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoalScheduler {

    private final FcmService fcmService;
    private final UserRepository userRepository; // 유저 + FCM 토큰 + 목표 정보 보유

    @Scheduled(cron = "0 0 9 * * ?") // 매일 오전 9시
    public void notifyUsersOfArrivedGoals() {
//        List<User> users = userRepository.findAllWithArrivingGoalsToday();
//
//        for (User user : users) {
//            String token = user.getFcmToken();
//            int count = user.getArrivingGoalsCount();
//
//            try {
//                fcmService.sendMessage(
//                        token,
//                        "🎯 오늘 도착한 목표!",
//                        "오늘 도착한 목표가 " + count + "개 있어요!"
//                );
//            } catch (Exception e) {
//                System.err.println("❌ FCM 전송 실패: " + e.getMessage());
//            }
//        }
    }
}
