package hoselabs.re_tap.domain.user.entity;

import hoselabs.re_tap.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_tb")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "profile_image_url", nullable = false)
    private String profileImageUrl;

    @Column(name = "nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked;

    @Column(name = "fcm_token")
    private String fcmToken;

    @Builder
    public User(Long id, String username, String provider, String profileImageUrl) {
        this.id = id;
        this.username = username;
        this.provider = provider;
        this.profileImageUrl = profileImageUrl;
        this.role = Role.USER;
        this.isBlocked = false;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }
    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}