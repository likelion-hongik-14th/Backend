package hello.hellospring.repository;

import hello.hellospring.Entity.Category;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CategoryRepository {
    private final EntityManager em;

    public CategoryRepository(EntityManager em) {
        this.em = em;
    }

    public Category save(Category category) {
        em.persist(category);
        return category;
    }

    public Optional<Category> findByName(String name) {
        return em.createQuery("select c from Category c where c.categoryName = :name", Category.class)
                .setParameter("name", name)
                .getResultList().stream().findFirst();
    }
}