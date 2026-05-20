package hello.hellospring.dto;

import hello.hellospring.Entity.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;

    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getArticleTitle();
        this.content = article.getArticleContent();
    }

}
