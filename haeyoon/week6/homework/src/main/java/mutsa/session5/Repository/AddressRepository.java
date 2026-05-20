package mutsa.session5.Repository;

import mutsa.session5.Entity.Address;
import mutsa.session5.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByMember(Member member);
}
