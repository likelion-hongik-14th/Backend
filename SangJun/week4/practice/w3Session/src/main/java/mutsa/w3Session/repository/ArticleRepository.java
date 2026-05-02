package mutsa.w3Session.repository;

import mutsa.w3Session.api.Article;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class ArticleRepository{

    private static final Map<Long, Article> store = new HashMap<>();
    private static Long sequence = 0L;

    public Article save(Article article){
        article.setId(++sequence);
        store.put(article.getId(), article);
        return article;
    }

    public Optional<Article> findById(long id){
        return Optional.ofNullable(store.get(id));
    }
}
