package hello.hellospring.repository;

import hello.hellospring.Entity.Like;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LikeRepository {
    private final EntityManager em;

    public void save(Like like) {
        em.persist(like);
    }

    public void delete(Like like) {
        em.remove(like);
    }

    // 중복 좋아요 방지 및 취소 시 찾기 위해 사용
    public Optional<Like> findByMemberAndArticle(Long memberId, Long articleId) {
        return em.createQuery("select l from Like l where l.member.id = :memberId and l.article.id = :articleId", Like.class)
                .setParameter("memberId", memberId)
                .setParameter("articleId", articleId)
                .getResultList().stream().findFirst();
    }
}