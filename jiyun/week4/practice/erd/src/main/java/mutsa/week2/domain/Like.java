package mutsa.week2.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "좋아요")
public class Like {
    @Id
    @Column(name = "좋아요아이디")
    private Long id;

    @Column(name = "좋아요수")
    private Long likeCount;

    @Column(name = "회원아이디", nullable = false)
    private Long memberId;

    @Column(name = "댓글아이디", nullable = false)
    private Long commentId;

    @Column(name = "기사아이디", nullable = false)
    private Long articleId;

    @Builder
    public Like(Long id, Long likeCount, Long memberId, Long commentId, Long articleId) {
        this.id = id;
        this.likeCount = likeCount;
        this.memberId = memberId;
        this.commentId = commentId;
        this.articleId = articleId;
    }
}