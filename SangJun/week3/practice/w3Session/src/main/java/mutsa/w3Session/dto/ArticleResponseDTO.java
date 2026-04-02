package mutsa.w3Session.dto;


import lombok.Getter;
import mutsa.w3Session.api.Article;

@Getter
public class ArticleResponseDTO{
    private Long id;
    private String title;
    private String content;

    public ArticleResponseDTO(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}