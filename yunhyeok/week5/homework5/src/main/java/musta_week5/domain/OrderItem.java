package musta_week5.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ORDERITEMS")
@NoArgsConstructor

public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer orderPrice;

    private Integer orderCount;



}
