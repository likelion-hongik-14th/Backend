package mutsa.session2.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Date;

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
    private Date date;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = false)
    private Member member;
}
