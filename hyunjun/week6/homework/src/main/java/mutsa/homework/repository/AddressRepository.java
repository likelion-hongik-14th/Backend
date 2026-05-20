package mutsa.homework.repository;

import mutsa.homework.domain.Address;
import mutsa.homework.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByUser_Id(Long userId);
    boolean existsByUser_IdAndAddressName(Long userId, String addressName);

}
