package mutsa.week2.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "상품")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "상품아이디")
    private Long id;

    @Column(name = "상품명", nullable = false, length = 100)
    private String name;

    @Column(name = "가격", nullable = false)
    private Long price;

    @Column(name = "재고", nullable = false)
    private Integer stock;

    @Builder
    public Product(String name, Long price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}
