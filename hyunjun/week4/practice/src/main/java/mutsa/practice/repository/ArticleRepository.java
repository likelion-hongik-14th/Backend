package mutsa.practice.repository;

import mutsa.practice.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByCreatedAt(LocalDateTime createdAt);
    //Optional<Article> findByUpdatedAt(LocalDateTime updatedAt);
    Optional<Article> findByTitle(String title);
    Optional<Article> findByContent(String content);
}
