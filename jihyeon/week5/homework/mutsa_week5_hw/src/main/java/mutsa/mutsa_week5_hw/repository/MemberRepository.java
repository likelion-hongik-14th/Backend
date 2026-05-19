package mutsa.mutsa_week5_hw.repository;

import mutsa.mutsa_week5_hw.domain.CartItem;
import mutsa.mutsa_week5_hw.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 수정: MemberRepository 생성
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
