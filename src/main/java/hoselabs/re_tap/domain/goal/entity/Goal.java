package hoselabs.re_tap.domain.goal.entity;

import hoselabs.re_tap.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Goal extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GoalStatus status;

    @Column(name = "score")
    private Integer score;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Builder
    public Goal(Long id, Long userId, String title, String content, Boolean isLocked,
                LocalDateTime arrivalDate, GoalStatus status) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.isLocked = isLocked;
        this.arrivalDate = arrivalDate;
        this.status = status;
    }

    public boolean isArrived() {
        return arrivalDate != null && arrivalDate.isBefore(LocalDateTime.now());
    }

    public boolean isRead() {
        return readAt != null;
    }

    public void markAsRead() {
        this.readAt = LocalDateTime.now();
    }

    public void markAsRead(LocalDateTime time) {
        this.readAt = time;
    }

    public void update(String title, String content, Boolean isLocked, LocalDateTime arrivalDate, GoalStatus status) {
        this.title = title;
        this.content = content;
        this.isLocked = isLocked;
        this.arrivalDate = arrivalDate;
        this.status = status;
    }

    public void feedbackGoal(Integer score, String feedback) {
        this.score = score;
        this.feedback = feedback;
    }
}
