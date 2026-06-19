package mutsa.hw5.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.util.ArrayList;
import java.util.List;

// JPA에게 이 클래스가 DB 테이블과 연동된 엔터티라는 걸 명시
@Entity
// get 메서드를 자동 생성 (ex.getName)
@Getter
// 파라미터 없는 기본 생성자를 Protected로 자동 생성
// Protected로 하는 이유는 외부에서 막 생성하지 못 하게 막기 위해서
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseEntity {

    // PK를 JPA에게 알려줌
    @Id
    // PK값 자동 생성, 대리키(키 값 자체로는 의미가 없는 거) 생성 때 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    // Cart 1 : Member 1을 의미
    // Cart를 조회할 때 현재 이 객체를 당장 가져오는 것이 아닌 실제 사용 시 DB에서 가져 옴
    @OneToOne(fetch = FetchType.LAZY)
    // DB에서 쓰이는 FK 컬럼명
    @JoinColumn(name = "member_id")
    private Member member;

    // 카트 1 : 카트아이템 N
    // 외래키 관리는 CartItem의 cart 필드가 진행
    // cascade는 카트의 변동사항을 카트아이템에 자동 반영
    // orphanRemoval은 부모가 연결이 끊기면 카트아이템도 자동으로 삭제
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    // CartItem을 객체로 가지는 List
    private List<CartItem> cartItems = new ArrayList<>();

    // 정적 팩토리 메서드
    // protected Cart(...)와 동일한 기능이지만, create라는 이름을 통해 의도가 명확해짐
    // static을 통해 객체를 만들고 호출할 필요 없이 바로 호출
    public static Cart create(Member member) {
        Cart cart = new Cart();
        cart.member = member;
        return cart;
    }
}
