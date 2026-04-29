package hello.hellospring.repository;

import hello.hellospring.Entity.Article;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ArticleRepository {
//    private static final Map<Long, Article> store = new HashMap<>();
//    private static long sequence = 0L;
//    //기사저장
//    public Article save(Article article) {
//        article.setId(++sequence);
//        store.put(article.getId(), article);
//        return article;
//    }
//    //기사 단건 조회
//    public Optional<Article> findById(Long id){
//        return Optional.ofNullable(store.get(id));
//    }

    private EntityManager em;

    public ArticleRepository(EntityManager em) {
        this.em = em;
    }

    //기사저장
    public Article save(Article article) {
        em.persist(article);
        return article;
    }

    //기사단건조회
    public Optional<Article> findById(Long id){
        Article article = em.find(Article.class, id);
        return Optional.ofNullable(article);
    }


}
