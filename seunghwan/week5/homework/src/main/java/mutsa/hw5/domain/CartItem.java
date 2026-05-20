package mutsa.hw5.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import mutsa.hw5.dto.cartitem.CartItemUpdateDto;

@Entity // JPA에게 이 클래스가 DB 테이블과 연동된 엔터티라는 걸 명시
@Getter // get 메서드를 자동 생성 (ex.getName)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 파라미터 없는 기본 생성자를 Protected로 자동 생성
// Protected로 하는 이유는 외부에서 막 생성하지 못 하게 막기 위해서
public class CartItem extends BaseEntity {

    @Id // PK를 JPA에게 알려줌
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK값 자동 생성, 대리키(키 값 자체로는 의미가 없는 거) 생성 때 사용
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    // CartItem N : Cart 1을 의미
    // CartItem을 조회할 때 현재 이 객체를 당장 가져오는 것이 아닌 실제 사용 시 DB에서 가져 옴

    @JoinColumn(name = "cart_id") // DB에서 쓰이는 FK 컬럼명
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY) // CartItem N : Product 1을 의미
    @JoinColumn(name = "product_id")
    private Product product;

    private int itemQuantity;

    public static CartItem create(Cart cart, Product product, int itemQuantity) {
    // 정적 책토리 메서드
    // protected CartItem(...)와 동일한 기능이지만, create라는 이름을 통해 의도가 명확해짐
    // static을 통해 객체를 만들고 호출할 필요 없이 바로 호출

        CartItem cartItem = new CartItem();
        cartItem.cart = cart;
        cartItem.product = product;
        cartItem.itemQuantity = itemQuantity;
        return cartItem;
    }

    public void update(CartItemUpdateDto dto) {
        // Setter를 쓰지 않음으로서 외부에서 값이 막 변경되는 것을 막고,
        // update라는 매서드를 씀으로서 직관적으로 업데이트 하는 것을 알 수 있음

        this.itemQuantity = dto.getItemQuantity();
    }

    public void addQuantity(int quantity) {

        this.itemQuantity += quantity;
    }
}