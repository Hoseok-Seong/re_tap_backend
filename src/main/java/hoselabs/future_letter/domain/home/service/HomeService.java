package hoselabs.future_letter.domain.home.service;

import hoselabs.future_letter.domain.home.dto.HomeResp;
import hoselabs.future_letter.domain.home.util.QuoteProvider;
import hoselabs.future_letter.domain.letter.dao.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final LetterRepository letterRepository;
    private final QuoteProvider quoteProvider;

    @Transactional(readOnly = true)
    public HomeResp getHome(Long userId) {
        // 1. 읽지 않은 도착 편지 수
        int unreadCount = letterRepository.countUnreadAndArrived(userId);

        // 2. 오늘의 한 문장
        HomeResp.Quote todayQuote = quoteProvider.getQuoteByDayOfYear(LocalDate.now().getDayOfYear());

        // 3. 최근 작성한 편지 3건
        List<HomeResp.RecentLetter> recentLetters = letterRepository.findRecentLetters(userId, 3).stream()
                .map(letter -> new HomeResp.RecentLetter(
                        letter.getTitle(),
                        letter.getCreatedAt().toLocalDate()
                ))
                .toList();

        // 4. 도착 예정 편지 3건 (한달 이내)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthLater = now.plusMonths(1);
        List<HomeResp.UpcomingLetter> upcomingLetters = letterRepository.findUpcomingLetters(userId, now, oneMonthLater, 3).stream()
                .map(letter -> new HomeResp.UpcomingLetter(
                        letter.getTitle(),
                        letter.getArrivalDate().toLocalDate()
                ))
                .toList();

        return new HomeResp(unreadCount, todayQuote, recentLetters, upcomingLetters);
    }
}
