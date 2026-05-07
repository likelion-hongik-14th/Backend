package hello.hellospring.repository;

import hello.hellospring.Entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberIdAndArticleId(Long memberId, Long articleId);
}