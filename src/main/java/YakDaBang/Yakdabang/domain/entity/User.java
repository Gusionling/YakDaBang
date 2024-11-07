package YakDaBang.Yakdabang.domain.entity;


import YakDaBang.Yakdabang.domain.dto.request.SignUpRequest;
import YakDaBang.Yakdabang.domain.entity.mapping.UserNews;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "serial_id", nullable = false, unique = true)
    private Long serialId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "is_login", columnDefinition = "TINYINT(1)")
    private Boolean isLogin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserNews> newsList = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Builder
    public User(Long serialId) {
        this.serialId = serialId;
        this.isLogin = true;
    }

    public static User signUp(Long serialId) {
        return User.builder()
                .serialId(serialId)
                .build();
    }

    public static User signUpByRequest(SignUpRequest request) {
        return User.builder()
                .serialId(request.getProviderId())
                .email(request.getEmail())
                .nickname(request.getNickName())
                .isLogin(false)
                .build();
    }
}
