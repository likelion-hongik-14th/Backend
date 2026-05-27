package mutsa.w5homework.repository;

import mutsa.w5homework.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
