package mutsa.session;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 메모리 저장소에서 ID를 세팅해주기 위해 추가
public class Article {

    private Long id;
    private String title;
    private String content;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
