package mutsa.week2.like;

import jakarta.persistence.*;
import lombok.*;
import mutsa.week2.article.Article;
import mutsa.week2.comment.Comment;
import mutsa.week2.member.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "좋아요")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "좋아요아이디")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "회원아이디", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "댓글아이디", nullable = false)
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "기사아이디", nullable = false)
    private Article article;

    @Builder
    public Like(Member member, Comment comment, Article article) {
        this.member = member;
        this.comment = comment;
        this.article = article;
    }
}
