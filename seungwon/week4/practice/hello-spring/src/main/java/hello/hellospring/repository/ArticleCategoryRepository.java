package hello.hellospring.repository;

import hello.hellospring.Entity.ArticleCategory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleCategoryRepository {
    private final EntityManager em;

    public ArticleCategoryRepository(EntityManager em) {
        this.em = em;
    }

    // 기사와 카테고리의 연결 정보를 저장하는 메서드
    public void save(ArticleCategory articleCategory) {
        em.persist(articleCategory);
    }
}