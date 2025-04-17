package hoselabs.future_letter.domain.letter.entity;

import hoselabs.future_letter.domain.user.entity.Role;
import hoselabs.future_letter.global.entity.BaseTimeEntity;
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
public class Letter extends BaseTimeEntity {
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
    private LetterStatus status;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Builder
    public Letter(Long id, Long userId, String title, String content, Boolean isLocked,
                  LocalDateTime arrivalDate, LetterStatus status) {
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
}
