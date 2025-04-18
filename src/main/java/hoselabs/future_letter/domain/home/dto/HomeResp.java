package hoselabs.future_letter.domain.home.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class HomeResp {
    private int unreadCount;
    private Quote todayQuote;
    private List<RecentLetter> recentLetters;
    private List<UpcomingLetter> upcomingLetters;

    @Getter
    @AllArgsConstructor
    public static class Quote {
        private String author;
        private String krContent;
        private String enContent;
    }

    @Getter
    @AllArgsConstructor
    public static class RecentLetter {
        private String title;
        private LocalDate createdAt;
    }

    @Getter
    @AllArgsConstructor
    public static class UpcomingLetter {
        private String title;
        private LocalDate arrivalDate;
    }
}
