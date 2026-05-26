package mutsa.hw5.repository;

import mutsa.hw5.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    // memberId로 배송지 전체 조회(GET용)
    List<Address> findAllByMember_MemberId(Long memberId);

    // addressId + memberId로 조회(PATCH + DELETE용)
    Optional<Address> findByAddressIdAndMember_MemberId(Long addressId, Long memberId);

    // 고객 당 주소 갯수 제한 카운트 쿼리
    long countByMember_MemberId(Long memberId);
}
