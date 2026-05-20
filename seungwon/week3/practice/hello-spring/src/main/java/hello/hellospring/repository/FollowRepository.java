package hello.hellospring.repository;

import hello.hellospring.Entity.Follow;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FollowRepository {
    private final EntityManager em;

    // 팔로우 관계 저장
    public void save(Follow follow) {
        em.persist(follow);
    }

    // 이미 팔로우 했는지 확인 (중복 방지용)
    public Optional<Follow> findByFollowerAndFollowing(Long followerId, Long followingId) {
        return em.createQuery("select f from Follow f where f.follower.id = :followerId and f.following.id = :followingId", Follow.class)
                .setParameter("followerId", followerId)
                .setParameter("followingId", followingId)
                .getResultList().stream().findFirst();
    }

    // 팔로우 취소 시 삭제 로직
    public void delete(Follow follow) {
        em.remove(follow);
    }
}