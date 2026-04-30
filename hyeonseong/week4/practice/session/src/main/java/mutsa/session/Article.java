package mutsa.session;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    private String title;
    private String content;
    private LocalDate date;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    public Article(String title, String content, Member member){
        this.title = title;
        this.content = content;
        this.member = member;
        this.date = LocalDate.now();
    }

}
