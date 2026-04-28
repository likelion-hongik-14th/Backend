package mutsa.week2.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "댓글")
public class Comment {
    @Id
    @Column(name = "댓글아이디")
    private Long id;

    @Column(name = "댓글제목", length = 50)
    private String title;

    @Lob
    @Column(name = "댓글내용", columnDefinition = "TEXT")
    private String content;

    @Column(name = "회원아이디", nullable = false)
    private Long memberId;

    @Column(name = "기사아이디", nullable = false)
    private Long articleId;

    @Builder
    public Comment(Long id, String title, String content, Long memberId, Long articleId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.articleId = articleId;
    }
}