package musta_week5.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = " OPTIONS")
@NoArgsConstructor

public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;

    private String optionName;

    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;


}
