package mutsa.homework.domain;

import jakarta.persistence.*;
import lombok.*;
import mutsa.homework.global.apiPayload.code.ProductErrorCode;
import mutsa.homework.global.apiPayload.exception.ProjectException;
import mutsa.homework.global.common.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;
    private int stock;

    @Builder(access = AccessLevel.PRIVATE)
    private Product(String name, int price, int stock){
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public static Product create(String name, int price, int stock){
        return Product.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .build();
    }

    public void increaseStock(int stock) { this.stock += stock; }

    public void decreaseStock(int stock) {
        int restStock = this.stock - stock;
        if (restStock < 0) {
            throw new ProjectException(ProductErrorCode.PRODUCT_OUT_OF_STOCK);
        }
        this.stock = restStock;
    }

    public void updatePrice(int price) { this.price = price; }

    public void verifyStock(int requestQuantity) {
        if (this.stock < requestQuantity) {
            throw new ProjectException(ProductErrorCode.PRODUCT_OUT_OF_STOCK);
        };
    }

}
