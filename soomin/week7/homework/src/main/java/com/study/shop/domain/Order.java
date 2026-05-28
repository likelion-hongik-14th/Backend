package com.study.shop.domain;

import com.study.shop.global.apiPayload.code.OrderErrorCode;
import com.study.shop.global.apiPayload.exception.ProjectException;
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
            throw new ProjectException(OrderErrorCode.ORDER_DELIVERY_COMPLETED);
        }

        if (this.status == OrderStatus.CANCELED) {
            throw new ProjectException(OrderErrorCode.ORDER_ALREADY_CANCELED);
        }

        this.status = OrderStatus.CANCELED;
    }

    public void updateStatus(OrderStatus status) {

        if (this.status == OrderStatus.CANCELED) {
            throw new ProjectException(OrderErrorCode.ORDER_CANCELED_STATUS_CANNOT_CHANGE);
        }

        if (this.status == OrderStatus.DELIVERY_COMPLETED) {
            throw new ProjectException(OrderErrorCode.ORDER_DELIVERED_STATUS_CANNOT_CHANGE);
        }

        if (this.status == status) {
            return;
        }

        if (!canChangeTo(status)) {
            throw new ProjectException(OrderErrorCode.ORDER_STATUS_CANNOT_CHANGE);
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
