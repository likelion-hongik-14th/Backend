package mutsa.api.repository;

import mutsa.api.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUsersId(Long usersId);

    Optional<Address> findByIdAndUsersId(Long id, Long usersId);
}
