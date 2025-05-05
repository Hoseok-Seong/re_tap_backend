package hoselabs.re_tap.domain.home.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HomeResp {
    private int unreadCount;
    private Quote todayQuote;
    private List<RecentGoal> recentGoals;
    private List<UpcomingGoal> upcomingGoals;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Quote {
        private String no;
        private String author;
        private String krContent;
        private String enContent;
        private String subject;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentGoal {
        private String title;
        private LocalDate createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpcomingGoal {
        private String title;
        private LocalDate arrivalDate;
    }
}
