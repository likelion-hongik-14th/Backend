package com.app.mutsa_shoppingmall.Entity;

import com.app.mutsa_shoppingmall.exception.ErrorCode;
import com.app.mutsa_shoppingmall.exception.GeneralException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.ORDER_COMPLETE;

    private LocalDateTime orderedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    public void cancel() {
        if (this.status == OrderStatus.DELIVERY_COMPLETE) {
            throw new GeneralException(ErrorCode.ORDER_CANCEL_FORBIDDEN);
        }
        this.status = OrderStatus.CANCEL;

        for (OrderItem item : orderItems) {
            item.getProduct().increaseStock(item.getQuantity());
        }
    }
}