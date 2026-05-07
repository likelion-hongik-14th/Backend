package mutsa.hw5.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.util.ArrayList;
import java.util.List;

@Entity // JPA에게 이 클래스가 DB 테이블과 연동된 엔터티라는 걸 명시
@Getter // get 메서드를 자동 생성 (ex.getName)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 파라미터 없는 기본 생성자를 Protected로 자동 생성
// Protected로 하는 이유는 외부에서 막 생성하지 못 하게 막기 위해서
public class Cart extends BaseEntity {

    @Id // PK를 JPA에게 알려줌
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK값 자동 생성, 대리키(키 값 자체로는 의미가 없는 거) 생성 때 사용
    private Long cartId;

    @OneToOne(fetch = FetchType.LAZY)
    // Cart 1 : Member 1을 의미
    // CartItem을 조회할 때 현재 이 객체를 당장 가져오는 것이 아닌 실제 사용 시 DB에서 가져 옴

    @JoinColumn(name = "member_id") // DB에서 쓰이는 FK 컬럼명
    private Member member;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    // 카트 1 : 카트아이템 N
    // 외래키 관리는 CartItem의 cart필드가 진행
    // cascade 뭐시기는 카트의 변동사항을 카트아이템에 자동 업데이트 느낌
    // 마지막은 부모가 연결이 끊기면 카트 아이텀도 자동으로 삭제

    private List<CartItem> cartItems = new ArrayList<>();
    // CartItem을 객체로 가지는 List

    public static Cart create(Member member) {
    // 정적 책토리 메서드
    // protected Cart(Member)와 동일한 기능이지만, create라는 이름을 통해 의도가 명확해짐
    // static을 통해 객체를 만들고 호출할 필요 없이 바로 호출
        Cart cart = new Cart();
        cart.member = member;
        return cart;
    }
}