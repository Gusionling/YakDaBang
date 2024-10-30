package YakDaBang.Yakdabang.domain.entity;


import YakDaBang.Yakdabang.domain.entity.mapping.UserNews;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "url")
    private String url;

    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserNews> savedByUsers = new ArrayList<>();

    @Builder
    public News(String title, String content, String url) {
        this.title = title;
        this.content = content;
        this.url = url;
    }

    public void addUserNews(UserNews userNews) {
        savedByUsers.add(userNews);
        userNews.setNews(this);
    }

}
