package hello.hellospring.repository;

import hello.hellospring.Entity.ArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory, Long> {
}