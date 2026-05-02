package mutsa.session.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)//Join이 꼭 필요할 때만 Join 하라는 의미
    @JoinColumn(name = "member_id", nullable = false)//FK는 보통 비어있지 않으므로
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article articleId;

    private String text;
    private LocalDateTime date;
}
