package mutsa.session2;

import mutsa.session2.Entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringJpaArticleRepository extends JpaRepository<Article, Long> { }