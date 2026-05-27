package mutsa.session5.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long deliveryId;

    public enum DeliveryStatus {
        PREPARING, SHIPPED, ONTHEWAY, OUTFORDELIVERY, DELIVERED
    }
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private String addressName;
    private String zipCode;
    private String baseAddress;
    private String detailAddress;
    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }
}