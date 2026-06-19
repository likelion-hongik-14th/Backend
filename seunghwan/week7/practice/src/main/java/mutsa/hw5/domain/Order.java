package mutsa.hw5.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.util.ArrayList;
import java.util.List;

// JPA에게 이 클래스가 DB 테이블과 연동된 엔터티라는 걸 명시
@Entity
// order는 SQL 예약어라 테이블명 명시
@Table(name = "orders")
// get 메서드를 자동 생성 (ex.getName)
@Getter
// 파라미터 없는 기본 생성자를 Protected로 자동 생성
// Protected로 하는 이유는 외부에서 막 생성하지 못 하게 막기 위해서
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    // PK를 JPA에게 알려줌
    @Id
    // PK값 자동 생성, 대리키(키 값 자체로는 의미가 없는 거) 생성 때 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    // Order N : Member 1을 의미
    // Order를 조회할 때 현재 이 객체를 당장 가져오는 것이 아닌 실제 사용 시 DB에서 가져 옴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // enum 값을 DB에 저장할 때 문자열로 저장하라는 뜻
    // 기본값이 EnumType.ORDINAL인데 이는 enum 순서대로 값이 저장됨
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    // 배송지 스냅샷 (주문 시점 고정)
    // addressId로 받을 시 나중에 주소가 변경되면 같이 변하게 됨
    @Column(nullable = false)
    private String receiverName;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    // 주문 1 : 주문아이템 N
    // 외래키 관리는 OrderItem의 order 필드가 진행
    // cascade는 주문의 변동사항을 주문아이템에 자동 반영
    // orphanRemoval은 부모가 연결이 끊기면 주문아이템도 자동으로 삭제
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    // OrderItem을 객체로 가지는 List
    private List<OrderItem> orderItems = new ArrayList<>();

    // 정적 팩토리 메서드
    // protected Order(...)와 동일한 기능이지만, create라는 이름을 통해 의도가 명확해짐
    // static을 통해 객체를 만들고 호출할 필요 없이 바로 호출
    public static Order create(Member member, String receiverName, String postalCode,
                               String address, String phoneNumber) {
        Order order = new Order();
        order.member = member;
        order.orderStatus = OrderStatus.ORDER_COMPLETED;
        order.receiverName = receiverName;
        order.postalCode = postalCode;
        order.address = address;
        order.phoneNumber = phoneNumber;
        return order;
    }

    // 인스턴스 메서드 -> 이미 존재하는 객체에서 호출
    public void changeStatus(OrderStatus status) {
        this.orderStatus = status;
    }
}
