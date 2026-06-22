package mutsa.session5.Entity;

import jakarta.persistence.*;
import lombok.*;
import mutsa.session5.global.apipayload.exception.OrderException;
import mutsa.session5.global.apipayload.exception.code.OrderErrorCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long orderId;

    public enum OrderStatus {
        ORDERPLACED, PAYMENTCONFIRMED, ORDERCANCELED, DELIVERED
    }
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.ORDERPLACED;

    private Long totalPrice;
    private String orderNumber;
    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Delivery delivery;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void updateStatus(OrderStatus newStatus) {
        if (this.orderStatus == OrderStatus.ORDERCANCELED) {
            throw new OrderException(OrderErrorCode.INVALID_ORDER_STATUS);
        }
        if (this.orderStatus == OrderStatus.DELIVERED) {
            throw new OrderException(OrderErrorCode.INVALID_ORDER_STATUS);
        }

        this.orderStatus = newStatus;
    }
}