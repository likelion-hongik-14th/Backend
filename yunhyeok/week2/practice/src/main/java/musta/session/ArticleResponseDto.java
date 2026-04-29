package musta.session;

import lombok.Getter;
import musta.session.Domain.Article;

@Getter
public class ArticleResponseDto {

    private Long id;
    private String title;
    private String content;

    public ArticleResponseDto(Article article) {
        this.id = article.getArticleId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }

}
