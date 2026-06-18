package mutsa.shop.repository;

import mutsa.shop.domain.Address;
import mutsa.shop.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByMemberId(Long memberId);
}
