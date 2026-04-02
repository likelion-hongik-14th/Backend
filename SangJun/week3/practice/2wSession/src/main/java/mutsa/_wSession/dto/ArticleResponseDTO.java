package mutsa._wSession.dto;


import lombok.Getter;
import mutsa._wSession.Application;
import mutsa._wSession.api.Article;

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