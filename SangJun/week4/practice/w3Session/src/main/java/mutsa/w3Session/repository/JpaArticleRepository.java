package mutsa.w3Session.repository;

import mutsa.w3Session.api.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaArticleRepository extends JpaRepository<Article, Long> {
}
