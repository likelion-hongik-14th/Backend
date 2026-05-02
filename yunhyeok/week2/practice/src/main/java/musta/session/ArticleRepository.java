package musta.session;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import musta.session.Domain.Article;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ArticleRepository {

    @PersistenceContext
    private EntityManager em;

    public Article save(Article article)
    {
        em.persist(article);
        return article;
    }

    public Optional<Article> findById(Long id){
        Article article = em.find(Article.class, id);
        return Optional.ofNullable(article);
    }
}
