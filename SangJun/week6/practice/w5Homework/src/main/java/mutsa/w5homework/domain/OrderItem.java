package mutsa.w5homework.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderPrice;
    private Long count;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public OrderItem(Long orderPrice, Long count, Product product) {
        this.orderPrice = orderPrice;
        this.count = count;
        this.product = product;
    }

    protected void connectOrder(Order order) {
        this.order = order;
    }

    //객체 스스로가 상태를 책임지도록 도메인 안에 메소드를 정의
    public static OrderItem createOrderItem(Product product, Long count, Long orderPrice) {
        OrderItem orderItem = OrderItem.builder()
                .orderPrice(orderPrice)
                .count(count)
                .product(product)
                .build();
        product.removeStock(count);
        return orderItem;
    }
    //원복 메서드
    public void cancle() {
        this.getProduct().addStock(this.getCount());
    }
}
