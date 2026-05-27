package mutsa.mutsa_week5_hw.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 주문
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // 상품
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // 주문 당시 상품명 snapshot
    private String productName;

    // 주문 당시 가격 snapshot
    private int orderPrice;

    // 주문 수량
    private int quantity;

    // 정적 팩토리 메서드
    public static OrderItem createOrderItem(Product product,
                                            int quantity) {

        // 재고 감소
        product.decreaseStock(quantity);

        return OrderItem.builder()
                .product(product)
                .productName(product.getName())
                .orderPrice(product.getPrice())
                .quantity(quantity)
                .build();
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
