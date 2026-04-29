package mutsa.session.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long article_id;

    private String title;
    private String content;
    private String article_date; //

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 작성자
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id") // 카테고리
    private Category category;

    public Article(String title, String content, Member member, Category category) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.category = category;
        this.article_date = LocalDate.now().toString();
    }
}