package mutsa.w5homework.repository;

import mutsa.w5homework.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    //회원 전체 주소 조회 쿼리 메서드
    List<Address> findByMemberId(Long memberId);
}
