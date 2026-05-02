package mutsa.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.session.domain.Article;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime date;
    private String memberName;

    // Entity를 DTO로 변환
    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.date = article.getCreatedAt();
        this.memberName = article.getMember().getName();
    }

}
