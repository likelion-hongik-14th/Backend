package mutsa.api.repository;

import mutsa.api.domain.Address;
import mutsa.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long>{
    List<Address> findAllByUser(User user);
}
