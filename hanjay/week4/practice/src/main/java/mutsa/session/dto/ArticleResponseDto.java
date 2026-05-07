package mutsa.session.dto;

import lombok.*;
import mutsa.session.domain.Article;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime date;

    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.date = article.getDate();
        if (article.getMember() != null) {
            this.authorName = article.getMember().getName();
        }
    }
}
