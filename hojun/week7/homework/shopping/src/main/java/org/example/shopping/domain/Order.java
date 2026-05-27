package org.example.shopping.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.shopping.domain.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String userName;
    private String userEmail;

    private String address;
    private String name;
    private String phone;
    private String zipcode;

    private LocalDateTime orderDate;
    private OrderStatus orderStatus;

    public static Order createOrder(User user, Address address) {

        return Order.builder()
                .user(user)
                .userName(user.getName())
                .userEmail(user.getEmail())
                .address(address.getAddress())
                .name(address.getName())
                .phone(address.getPhone())
                .zipcode(address.getZipcode())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .orderItems(new ArrayList<>())
                .build();
    }
    public void cancleOrder(){
        if(this.orderStatus == OrderStatus.DELIVERED){
            throw new IllegalStateException("이미 배송 완료된 주문은 취소가 불가능합니다.");
        }

        this.orderStatus = OrderStatus.CANCEL;

        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }
    public int getTotalPrice() {
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }

    public void disconnectUser() {
        this.user = null;
    }
}
