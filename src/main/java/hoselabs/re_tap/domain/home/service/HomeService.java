package hoselabs.re_tap.domain.home.service;

import hoselabs.re_tap.domain.goal.dao.GoalRepository;
import hoselabs.re_tap.domain.home.dto.HomeResp;
import hoselabs.re_tap.domain.home.util.QuoteProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final GoalRepository goalRepository;
    private final QuoteProvider quoteProvider;

    @Transactional(readOnly = true)
    public HomeResp getHome(Long userId) {
        // 1. 읽지 않은 도착 목표 수
        int unreadCount = goalRepository.countUnreadAndArrived(userId);

        // 2. 오늘의 한 문장
        HomeResp.Quote todayQuote = quoteProvider.getQuoteByDayOfYear(LocalDate.now().getDayOfYear());

        // 3. 최근 작성한 목표 3건
        List<HomeResp.RecentGoal> recentGoals = goalRepository.findRecentgoals(userId, 3).stream()
                .map(goal -> new HomeResp.RecentGoal(
                        goal.getTitle(),
                        goal.getCreatedAt().toLocalDate()
                ))
                .toList();

        // 4. 도착 예정 목표 3건 (한달 이내)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthLater = now.plusMonths(1);
        List<HomeResp.UpcomingGoal> upcomingGoals = goalRepository.findUpcominggoals(userId, now, oneMonthLater, 3).stream()
                .map(goal -> new HomeResp.UpcomingGoal(
                        goal.getTitle(),
                        goal.getArrivalDate().toLocalDate()
                ))
                .toList();

        return new HomeResp(unreadCount, todayQuote, recentGoals, upcomingGoals);
    }
}
