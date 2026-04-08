package practice.session;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Article {

    private Long id;
    private String title;
    private String content;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
