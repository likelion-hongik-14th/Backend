package mutsa.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.api.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private Long total_price;

    @Column(nullable = false)
    private LocalDateTime date;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public static Orders create(Users users, Address address) {
        Orders orders = new Orders();
        orders.users = users;
        orders.address = address;
        orders.status = OrderStatus.ORDER_COMPLETED;
        orders.total_price = 0L;
        orders.date = LocalDateTime.now();
        return orders;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
        this.orderItems.add(orderItem);
        this.total_price += orderItem.calculateTotalPrice();
    }

    public void completePayment() {
        this.status = OrderStatus.PAYMENT_COMPLETED;
    }

    public void completeDelivery() {
        this.status = OrderStatus.DELIVERY_COMPLETED;
    }

    public void cancel() {
        if (this.status == OrderStatus.DELIVERY_COMPLETED) {
            throw new IllegalArgumentException("배송 완료된 주문은 취소할 수 없습니다");
        }
        if (this.status == OrderStatus.CANCELED) {
            throw new IllegalArgumentException("이미 취소된 주문입니다.");
        }
        this.orderItems.forEach(OrderItem::restoreStock);
        this.status = OrderStatus.CANCELED;
    }
}
