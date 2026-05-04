package musta_week5.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ORDERITEMS")
@NoArgsConstructor

public class Orderitems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;


    @ManyToOne
    @JoinColumn(name = "option_id")
    private Options option;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    private Integer orderPrice;

    private Integer orderCount;



}
