package mutsa.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    private String addressName;
    private String fullAddress;

    // 영수증(Order)이 파기되면 그 안의 품목(OrderItem)도 함께 파기됨
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDate;

    @Builder(access = AccessLevel.PRIVATE)
    private Order(User user, Address address, OrderStatus status, LocalDateTime orderDate) {
        this.user = user;
        this.address = address;
        this.addressName = address.getAddressName();
        this.fullAddress = address.getAddress();
        this.status = status;
        this.orderDate = orderDate;
    }

    // 양방향 연관관계 편의 메소드
    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // Order 생성 공식 창구, 생성 메소드
    public static Order createOrder(User user, Address address, List<OrderItem> orderItems){
        Order order = new Order();
        order.user = user;
        order.address = address;

        order.addressName = address.getAddressName();
        order.fullAddress = address.getAddress();

        order.status = OrderStatus.ORDERED; // 처음 생성되면 상태는 '주문 완료'
        order.orderDate = LocalDateTime.now();
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return order;
    }

    // 비즈니스 로직: 주문 취소
    public void cancel(){
        if (this.status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("배송 완료된 상품은 취소가 불가능합니다.");
        }

        if (this.status == OrderStatus.CANCELED) {
            throw new IllegalStateException("이미 취소된 주문입니다.");
        }
        this.status = OrderStatus.CANCELED;

        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // 비즈니스 로직: 배송 완료 처리
    public void completeDelivery() {
        if (this.status == OrderStatus.CANCELED) {
            throw new IllegalStateException("취소된 주문은 배송 완료 처리가 불가능합니다.");
        }
        this.status = OrderStatus.DELIVERED;
    }

    // 비즈니스 로직: 결제 완료 처리
    public void payOrder(){
        if (this.status == OrderStatus.CANCELED){
            throw new IllegalStateException("취소된 주문은 결제할 수 없습니다.");
        }
        if (this.status == OrderStatus.DELIVERED){
            throw new IllegalStateException("이미 배송 완료된 주문입니다.");
        }
        this.status = OrderStatus.PAID;
    }
}
