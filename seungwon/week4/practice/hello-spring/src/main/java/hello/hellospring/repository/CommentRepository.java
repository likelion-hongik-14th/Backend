package hello.hellospring.repository;

import hello.hellospring.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Spring Data JPA 메서드 네이밍 규칙 적용
    List<Comment> findByArticleId(Long articleId);
}