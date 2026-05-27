package com.study.shop.repository;

import com.study.shop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByMemberIdOrderByOrderedAtDesc(Long memberId);

    Optional<Order> findByIdAndMemberId(Long orderId, Long memberId);

}
