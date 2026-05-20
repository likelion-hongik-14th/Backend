package mutsa.w5homework.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
//예약어 ORDER와의 충돌 방지
@Table(name = "orders")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Enum text가 DB에 안전하게 저장되도록
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    //영속성 설정으로 orderItems를 묶어줌
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order(OrderStatus orderStatus, LocalDateTime orderDate, Member member, Address address) {
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.member = member;
        this.address = address;
    }

    //연관관계 메소드
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.connectOrder(this);
    }

    public void cancle(){
        if(this.orderStatus == OrderStatus.DELIVERED || this.orderStatus == OrderStatus.DELIVERING){
            throw new IllegalArgumentException("배송중입니다.");
        }
        this.orderStatus = OrderStatus.CANCELED;
        //원복
        for(OrderItem orderItem : orderItems){
            orderItem.cancle();
        }
    }

}
