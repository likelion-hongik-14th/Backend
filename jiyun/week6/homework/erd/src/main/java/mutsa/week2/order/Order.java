package mutsa.week2.order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.week2.address.Address;
import mutsa.week2.common.error.BusinessException;
import mutsa.week2.common.error.ErrorCode;
import mutsa.week2.member.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "주문")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "주문아이디")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "회원아이디", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "상태", nullable = false, length = 30)
    private OrderStatus status;

    @Column(name = "주문일시", nullable = false)
    private LocalDateTime orderedAt;

    @Column(name = "배송지명", nullable = false, length = 30)
    private String shippingName;

    @Column(name = "우편번호", nullable = false, length = 10)
    private String shippingZipCode;

    @Column(name = "주소", nullable = false, length = 200)
    private String shippingAddress;

    @Column(name = "전화번호", nullable = false, length = 20)
    private String shippingPhone;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Builder
    public Order(Member member, Address address) {
        this.member = member;
        this.status = OrderStatus.ORDER_COMPLETED;
        this.orderedAt = LocalDateTime.now();
        this.shippingName = address.getName();
        this.shippingZipCode = address.getZipCode();
        this.shippingAddress = address.getAddress();
        this.shippingPhone = address.getPhone();
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
        item.assignOrder(this);
    }

    public void pay() {
        if (this.status != OrderStatus.ORDER_COMPLETED) {
            throw new BusinessException(ErrorCode.INVALID_ORDER_STATUS);
        }
        this.items.forEach(item -> item.getProduct().decreaseStock(item.getQuantity()));
        this.status = OrderStatus.PAYMENT_COMPLETED;
    }

    public void deliver() {
        if (this.status != OrderStatus.PAYMENT_COMPLETED) {
            throw new BusinessException(ErrorCode.INVALID_ORDER_STATUS);
        }
        this.status = OrderStatus.DELIVERY_COMPLETED;
    }

    public void cancel() {
        if (this.status == OrderStatus.DELIVERY_COMPLETED) {
            throw new BusinessException(ErrorCode.ORDER_NOT_CANCELABLE);
        }
        if (this.status == OrderStatus.CANCELLED) {
            throw new BusinessException(ErrorCode.INVALID_ORDER_STATUS);
        }
        if (this.status == OrderStatus.PAYMENT_COMPLETED) {
            this.items.forEach(item -> item.getProduct().restoreStock(item.getQuantity()));
        }
        this.status = OrderStatus.CANCELLED;
    }

    public long getTotalPrice() {
        return this.items.stream()
                .mapToLong(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}
