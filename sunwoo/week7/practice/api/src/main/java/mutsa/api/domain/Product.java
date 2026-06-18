package mutsa.api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false)
    private String description;

    public static Product create(String name, int price, int stock, String description) {
        return Product.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .description(description)
                .build();
    }

    public void decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (this.stock < quantity) {
            throw new IllegalArgumentException("Requested quantity exceeds available stock. Available stock: " + this.stock);
        }
        this.stock -= quantity;
    }

    public void restoreStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.stock += quantity;
    }
}
