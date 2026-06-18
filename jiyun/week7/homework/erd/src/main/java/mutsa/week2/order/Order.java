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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.week2.address.Address;
import mutsa.week2.global.apiPayload.exception.BusinessException;
import mutsa.week2.member.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private OrderStatus status;

    @Column(name = "ordered_at", nullable = false)
    private LocalDateTime orderedAt;

    @Column(name = "shipping_name", nullable = false, length = 30)
    private String shippingName;

    @Column(name = "shipping_zip_code", nullable = false, length = 10)
    private String shippingZipCode;

    @Column(name = "shipping_address", nullable = false, length = 200)
    private String shippingAddress;

    @Column(name = "shipping_phone", nullable = false, length = 20)
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
            throw new BusinessException(OrderErrorCode.INVALID_ORDER_STATUS);
        }
        this.items.forEach(item -> item.getProduct().decreaseStock(item.getQuantity()));
        this.status = OrderStatus.PAYMENT_COMPLETED;
    }

    public void deliver() {
        if (this.status != OrderStatus.PAYMENT_COMPLETED) {
            throw new BusinessException(OrderErrorCode.INVALID_ORDER_STATUS);
        }
        this.status = OrderStatus.DELIVERY_COMPLETED;
    }

    public void cancel() {
        if (this.status == OrderStatus.DELIVERY_COMPLETED) {
            throw new BusinessException(OrderErrorCode.ORDER_NOT_CANCELABLE);
        }
        if (this.status == OrderStatus.CANCELLED) {
            throw new BusinessException(OrderErrorCode.INVALID_ORDER_STATUS);
        }
        if (this.status == OrderStatus.PAYMENT_COMPLETED) {
            this.items.forEach(item -> item.getProduct().restoreStock(item.getQuantity()));
        }
        this.status = OrderStatus.CANCELLED;
    }

    public BigDecimal getTotalPrice() {
        return this.items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
