package musta_week5.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@Table(name = "PRODUCTS")
@NoArgsConstructor


public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private LocalDateTime createdAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


}
