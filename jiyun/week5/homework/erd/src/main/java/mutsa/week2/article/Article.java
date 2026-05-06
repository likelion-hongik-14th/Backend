package mutsa.week2.article;

import jakarta.persistence.*;
import lombok.*;
import mutsa.week2.category.Category;
import mutsa.week2.member.Member;

@Entity
@Getter // 필드 조회를 위해 필요
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "기사")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "기사아이디")
    private Long id;

    @Column(nullable = false, length = 40)
    private String title;

    @Lob
    @Column(name = "내용")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "회원아이디", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "카테고리아이디", nullable = false)
    private Category category;

    @Builder
    public Article(String title, String content, Member member, Category category) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.category = category;
    }
}
