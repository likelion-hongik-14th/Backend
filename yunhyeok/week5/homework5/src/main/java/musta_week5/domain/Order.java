package musta_week5.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "ORDERS")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private Integer totalPrice;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliverStatus deliverStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void updateStatus(OrderStatus status){
        this.status = status;
    }

    public void updateDeliverStatus(DeliverStatus deliverStatus) {
        this.deliverStatus = deliverStatus;
    }


}
