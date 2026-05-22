package mutsa.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @Builder
    public Product(String name, Integer price, Integer stock, String description, ProductStatus status){
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.status = (status != null) ? status : ProductStatus.SELL;
    }

    // 재고 차감 비즈니스 로직 (주문시 호출됨)
    public void removeStock(int quantity){
        int restStock = this.stock - quantity;
        if (restStock < 0){
            throw new IllegalStateException("재고가 부족합니다.");
        }
        this.stock = restStock;

        if (this.stock == 0){
            this.status = ProductStatus.SOLD_OUT;
        }
    }

    // 재고 원복 비즈니스 로직 (주문 취소시 호출됨)
    public void addStock(int quantity){
        if (this.status == ProductStatus.SOLD_OUT && quantity > 0){
            this.status = ProductStatus.SELL;
        }

        this.stock += quantity;
    }

    // 관리자용 상품 정보 수정 비즈니스 로직
    public void updateProduct(String name, int price, int stock, String description, ProductStatus status) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        if (status != null) {
            this.status = status;
        }
    }
}
