package mutsa.w5homework.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long price;
    private Long stock;
    private String description;

    @Builder
    public Product(String name, Long price, Long stock, String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }
    public void removeStock(Long count) {
        if(this.stock < count){
            throw new IllegalArgumentException("상품 재고가 부족합니다");
        }
        if(count == null || count < 0){
            throw new IllegalArgumentException("허용되지 않은 재고 범위입니다.");
        }
        this.stock -= count;
    }

    public void addStock(Long count) {
        this.stock += count;
    }
}
