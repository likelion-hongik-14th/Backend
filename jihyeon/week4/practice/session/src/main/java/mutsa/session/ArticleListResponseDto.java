package mutsa.session;

import lombok.Getter;
import mutsa.session.domain.Article;

@Getter
public class ArticleListResponseDto {
    private Long id;
    private String title;
    private String memberName;

    public ArticleListResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.memberName = article.getMember().getName();
    }
}
