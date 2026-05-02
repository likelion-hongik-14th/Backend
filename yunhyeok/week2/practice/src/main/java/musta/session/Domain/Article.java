package musta.session.Domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artciel_id")
    private Long articleId;
    private String title;
    private String content;
    private String day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    public Article(String title, String content) {

        this.title = title;
        this.content = content;
        this.day = LocalDate.now().toString();


    }

}
