package hello.hellospring.repository;

import hello.hellospring.Entitiy.Article;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ArticleRepository {
    private static final Map<Long, Article> store = new HashMap<>();
    private static long sequence = 0L;

    //기사저장
    public Article save(Article article) {
        article.setId(++sequence);
        store.put(article.getId(), article);
        return article;
    }

    //기사 단건 조회
    public Optional<Article> findById(Long id){
        return Optional.ofNullable(store.get(id));
    }
}
