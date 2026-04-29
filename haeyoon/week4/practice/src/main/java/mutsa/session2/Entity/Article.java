package mutsa.session2.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter

public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDateTime date;

    @Builder
    public Article(String title, String content, LocalDateTime date, Member member) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.member = member;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = false)
    private Member member;
}
