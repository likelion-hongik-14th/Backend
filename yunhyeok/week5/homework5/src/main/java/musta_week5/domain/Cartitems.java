package musta_week5.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CARTITEMS")
@Getter
@NoArgsConstructor

public class Cartitems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private Options option;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

}
