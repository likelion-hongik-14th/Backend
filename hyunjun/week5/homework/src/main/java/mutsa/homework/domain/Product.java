package mutsa.homework.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

}
