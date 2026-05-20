package mutsa.hw5.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import mutsa.hw5.dto.product.ProductUpdateDto;

@Entity // JPA에게 이 클래스가 DB 테이블과 연동된 엔터티라는 걸 명시
@Getter // get 메서드를 자동 생성 (ex.getName)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 파라미터 없는 기본 생성자를 Protected로 자동 생성
// Protected로 하는 이유는 외부에서 막 생성하지 못 하게 막기 위해서
public class Product extends BaseEntity {

    @Id // PK를 JPA에게 알려줌
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK값 자동 생성, 대리키(키 값 자체로는 의미가 없는 거) 생성 때 사용
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Long productPrice;

    @Column(nullable = false)
    private Long productStock;


    public static Product create(String productName, Long productPrice, Long productStock) {
    // 정적 책토리 메서드
    // protected Product(...)와 동일한 기능이지만, create라는 이름을 통해 의도가 명확해짐
    // static을 통해 객체를 만들고 호출할 필요 없이 바로 호출

        Product product = new Product();
        product.productName = productName;
        product.productPrice = productPrice;
        product.productStock = productStock;
        return product;
    }

    public void checkStock(int quantity) {
        if (quantity > this.productStock) {
            throw new RuntimeException("재고가 부족합니다. (재고: " + this.productStock + ")");
        }
    }

    public void update(ProductUpdateDto dto) {
    // PATCH로 메서드를 설정했기 때문에 일부만 수정하게 할려고
    // Setter를 쓰지 않음으로서 외부에서 값이 막 변경되는 것을 막고,
    // update라는 매서드를 씀으로서 직관적으로 업데이트 하는 것을 알 수 있음

        if (dto.getProductName() != null) {
            this.productName = dto.getProductName();
        }
        if (dto.getProductPrice() != null) {
            this.productPrice = dto.getProductPrice();
        }
        if (dto.getProductStock() != null) {
            this.productStock = dto.getProductStock();
        }
    }
}