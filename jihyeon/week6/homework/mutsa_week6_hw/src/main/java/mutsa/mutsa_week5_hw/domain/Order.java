package mutsa.mutsa_week5_hw.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 주문 회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 주문 상태
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 주문 시간
    private LocalDateTime orderedAt;

    // 배송 정보 snapshot
    private String deliveryName;
    private String deliveryZipcode;
    private String deliveryAddress;
    private String deliveryPhone;

    // 주문 상품 목록
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 정적 팩토리 메서드
    public static Order createOrder(Member member,
                                    Address address) {

        return Order.builder()
                .member(member)
                .status(OrderStatus.ORDERED)
                .orderedAt(LocalDateTime.now())
                .deliveryName(address.getName())
                .deliveryZipcode(address.getZipcode())
                .deliveryAddress(address.getAddress())
                .deliveryPhone(address.getPhone())
                .build();
    }

    // 연관 관계 메서드
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // 주문 취소 메서드
    public void cancel() {
        if (this.status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("배송 완료된 주문은 취소할 수 없습니다.");
        }

        this.status = OrderStatus.CANCELED;

        // 재고 원복 로직
        for (OrderItem item : orderItems) {
            item.getProduct().increaseStock(item.getQuantity());
        }
    }
}
