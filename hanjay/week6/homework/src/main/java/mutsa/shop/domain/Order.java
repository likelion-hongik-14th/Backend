package mutsa.shop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    public static Order createOrder(Member member, Address address, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setAddress(address);
        order.setStatus(OrderStatus.COMPLETED); // 초기 상태: 주문 완료
        order.setOrderDate(LocalDateTime.now());

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem); // 연관관계 편의 메서드 호출
        }
        return order;
    }

    // 주문 취소 로직
    public void cancel() {
        // 요구사항: '배송 완료' 전 단계에서만 취소 가능
        if (this.status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("이미 배송 완료된 주문은 취소할 수 없습니다.");
        }

        this.status = OrderStatus.CANCELED; // 상태 변경

        // 주문에 매핑된 상품들의 재고를 전부 원래대로 복구
        for (OrderItem orderItem : this.orderItems) {
            orderItem.cancel();
        }
    }

    // 연관관계 편의 메서드
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
