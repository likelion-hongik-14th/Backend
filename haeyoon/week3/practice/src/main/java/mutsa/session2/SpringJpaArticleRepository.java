package mutsa.session2;

import mutsa.session2.Entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

    public interface SpringJpaArticleRepository extends JpaRepository<Article, Long> {
        @Override
        Optional<Article> findById(Long id);
    }
}
