package mutsa.homework.domain;

import jakarta.persistence.*;
import lombok.*;
import mutsa.homework.global.common.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Builder(access = AccessLevel.PRIVATE)
    private Order(User user, Address address, OrderStatus status){
        this.user = user;
        this.address = address;
        this.status = status;
    }

    public static Order create(User user, Address address){
        return Order.builder()
                .user(user)
                .address(address)
                .status(OrderStatus.ORDERED)
                .build();
    }

    public void cancelOrder() {
        if (this.status == OrderStatus.SHIPPING || this.status == OrderStatus.DELIVERED) {
            throw new IllegalArgumentException("배송 중이거나 완료된 상품은 취소할 수 없습니다.");
        }

        this.status = OrderStatus.CANCELED;

        for (OrderItem orderItem : this.orderItems) {
            orderItem.cancelOrderItem();
        }
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public void validateUser(Long userId){
        if(!this.getUser().getId().equals(userId)){
            throw new IllegalArgumentException("잘못된 접근입니다. (권한 없음)");
        }
    }

    public int getTotalPrice(){
        return orderItems.stream()
                .mapToInt(OrderItem::calculatePrice)
                .sum();
    }
}
