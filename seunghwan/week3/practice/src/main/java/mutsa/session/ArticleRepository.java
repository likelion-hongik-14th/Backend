package mutsa.session;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ArticleRepository {
    // DB 역할을 대신할 Map과 자동 증가할 ID
    private static final Map<Long, Article> store = new HashMap<>();
    private static long sequence = 0L;

    // 기사 저장
    public Article save(Article article) {
        article.setId(++sequence);
        store.put(article.getId(), article);
        return article;
    }

    // 기사 단건 조회
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
}
