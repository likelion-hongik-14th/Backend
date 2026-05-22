package mutsa.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 당시 가격 (이후 상품 가격이 바뀌어도 영수증 가격은 유지)
    private int count; // 주문 수량

    // 생성 메소드: 주문 항목이 생성될 때 자동으로 상품의 재고가 깎임
    public static OrderItem createOrderItem(Product product, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.product = product;
        orderItem.orderPrice = orderPrice;
        orderItem.count = count;

        product.removeStock(count);
        return orderItem;
    }

    // 연관관계 매핑용 (Order 엔티티에서 호출됨)
    public void setOrder(Order order){
        this.order = order;
    }

    // 비즈니스 로직: 개별 품목 취소시, 차감되었던 재고를 다시 원복
    public void cancel(){
        getProduct().addStock(count);
    }
}
