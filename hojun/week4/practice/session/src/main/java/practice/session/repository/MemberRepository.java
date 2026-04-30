package practice.session.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.session.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
