package musta_week5.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@Table(name = "PRODUCTS")
@NoArgsConstructor


public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    private Integer price;

    private LocalDateTime createdAt;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


}
