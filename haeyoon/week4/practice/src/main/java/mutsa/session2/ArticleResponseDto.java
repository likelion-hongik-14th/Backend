package mutsa.session2;

import lombok.Getter;
import mutsa.session2.Entity.Article;

@Getter
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;

    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
