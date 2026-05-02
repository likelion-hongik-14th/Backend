package hello.hellospring.repository;

import hello.hellospring.Entity.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    // 특정 기사에 달린 모든 댓글을 가져오고 싶을 때 사용하는 메서드
    public List<Comment> findByArticleId(Long articleId) {
        return em.createQuery("select c from Comment c where c.article.id = :articleId", Comment.class)
                .setParameter("articleId", articleId)
                .getResultList();
    }
}