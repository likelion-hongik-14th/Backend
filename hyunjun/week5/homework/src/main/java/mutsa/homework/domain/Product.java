package mutsa.homework.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;
    private int stock;

    public static Product create(String name, int price, int stock){
        return Product.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .build();
    }

    public void increaseStock(int stock) { this.stock += stock; }

    public void updatePrice(int price) { this.price = price; }

    public void verifyStock(int requestQuantity) {
        if (this.stock < requestQuantity) {
            throw new IllegalArgumentException("재고가 부족합니다. (현재 잔여 재고: " + this.stock + "개");
        };
    }

}
