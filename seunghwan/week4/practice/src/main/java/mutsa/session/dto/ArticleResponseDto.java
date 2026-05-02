package mutsa.session.dto;

import lombok.Getter;
import mutsa.session.domain.Article;

@Getter
public class ArticleResponseDto {
    private Long articleId;
    private String title;
    private String content;
    private Long memberId;
    private Long categoryId;

    // Entity를 DTO로 변환
    public ArticleResponseDto(Article article) {
        this.articleId = article.getArticleId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.memberId = article.getMember().getMemberId();
        this.categoryId = article.getCategory().getCategoryId();
    }
}