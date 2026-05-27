package com.study.shop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String addressName;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private LocalDateTime orderedAt;

    public Order(Member member, Address address, int totalPrice) {
        this.member = member;

        this.addressName = address.getAddressName();
        this.zipCode = address.getZipCode();
        this.address = address.getAddress();
        this.detailAddress = address.getDetailAddress();
        this.phoneNumber = address.getPhoneNumber();

        this.totalPrice = totalPrice;
        this.status = OrderStatus.ORDER_COMPLETED;
        this.orderedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (this.status == OrderStatus.DELIVERY_COMPLETED) {
            throw new IllegalArgumentException("배송 완료된 주문은 취소할 수 없습니다.");
        }

        if (this.status == OrderStatus.CANCELED) {
            throw new IllegalArgumentException("이미 취소된 주문입니다.");
        }

        this.status = OrderStatus.CANCELED;
    }

    public void updateStatus(OrderStatus status) {

        if (this.status == OrderStatus.CANCELED) {
            throw new IllegalArgumentException("취소된 주문의 상태는 변경할 수 없습니다.");
        }

        if (this.status == OrderStatus.DELIVERY_COMPLETED) {
            throw new IllegalArgumentException("배송 완료된 주문의 상태는 변경할 수 없습니다.");
        }

        if (this.status == status) {
            return;
        }

        if (!canChangeTo(status)) {
            throw new IllegalArgumentException("변경할 수 없는 주문 상태입니다.");
        }

        this.status = status;
    }

    private boolean canChangeTo(OrderStatus newStatus) {
        if (this.status == OrderStatus.ORDER_COMPLETED) {
            return newStatus == OrderStatus.PAYMENT_COMPLETED
                    || newStatus == OrderStatus.CANCELED;
        }

        if (this.status == OrderStatus.PAYMENT_COMPLETED) {
            return newStatus == OrderStatus.DELIVERY_COMPLETED
                    || newStatus == OrderStatus.CANCELED;
        }

        return false;
    }

}
