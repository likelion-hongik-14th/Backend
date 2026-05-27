package mutsa.shop.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;
    private Long quantity;
    private Long orderPrice;

    public static OrderItem createOrderItem(Product product, Long quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setName(product.getName());         // 주문 시점 상품명 박제
        orderItem.setOrderPrice(product.getPrice());   // 주문 시점 가격 박제
        orderItem.setQuantity(quantity);
        return orderItem;
    }

    // 주문 취소 시 차감되었던 재고를 다시 원래대로 되돌리는 메서드
    public void cancel() {
        this.getProduct().addStock(this.quantity);
    }
}
