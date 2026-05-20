package com.app.mutsa_shoppingmall.Repository;

import com.app.mutsa_shoppingmall.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAll();
}