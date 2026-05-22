package mutsa.mutsa_week5_hw.repository;

import mutsa.mutsa_week5_hw.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    // 특정 회원 배송지 목록 조회
    List<Address> findByMemberId(Long memberId);
}
