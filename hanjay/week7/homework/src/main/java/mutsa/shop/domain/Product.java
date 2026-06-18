package mutsa.shop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long price;
    private Long stock;

    public void removeStock(Long quantity) {
        long restStock = this.stock - quantity;
        if (restStock < 0) {
            throw new IllegalStateException("상품의 재고가 부족합니다. (현재 재고: " + this.stock + ")");
        }
        this.stock = restStock;
    }


    public void addStock(Long quantity) {
        this.stock += quantity;
    }
}
