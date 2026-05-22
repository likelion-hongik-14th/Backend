package mutsa.session5.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product {
    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long productId;

    private String name;
    private Long price;
    private int stock;

    public void addStock(int quantity) {
        this.stock += quantity;
    }
    public void removeStock(int quantity) {
        int presentStock = this.stock - quantity;
        if (presentStock < 0) {
            throw new RuntimeException("재고가 부족합니다.");
        }
        this.stock = presentStock;
    }
}