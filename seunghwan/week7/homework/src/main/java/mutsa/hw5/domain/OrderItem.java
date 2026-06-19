package mutsa.hw5.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

// JPA에게 이 클래스가 DB 테이블과 연동된 엔터티라는 걸 명시
@Entity
// get 메서드를 자동 생성 (ex.getName)
@Getter
// 파라미터 없는 기본 생성자를 Protected로 자동 생성
// Protected로 하는 이유는 외부에서 막 생성하지 못 하게 막기 위해서
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    // PK를 JPA에게 알려줌
    @Id
    // PK값 자동 생성, 대리키(키 값 자체로는 의미가 없는 거) 생성 때 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    // OrderItem N : Order 1을 의미
    // OrderItem을 조회할 때 현재 이 객체를 당장 가져오는 것이 아닌 실제 사용 시 DB에서 가져 옴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // OrderItem N : Product 1을 의미
    // OrderItem을 조회할 때 현재 이 객체를 당장 가져오는 것이 아닌 실제 사용 시 DB에서 가져 옴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 상품 스냅샷 (주문 시점 고정)
    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Long productPrice;

    @Column(nullable = false)
    private Long itemQuantity;

    // 정적 팩토리 메서드
    // protected OrderItem(...)와 동일한 기능이지만, create라는 이름을 통해 의도가 명확해짐
    // static을 통해 객체를 만들고 호출할 필요 없이 바로 호출
    public static OrderItem create(Order order, Product product, Long quantity) {
        OrderItem item = new OrderItem();
        item.order = order;
        item.product = product;
        item.productName = product.getProductName();
        item.productPrice = product.getProductPrice();
        item.itemQuantity = quantity;
        return item;
    }
}
