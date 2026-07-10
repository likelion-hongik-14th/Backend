package mutsa.mutsa_week5_hw.domain;

import jakarta.persistence.*;
import lombok.*;
import mutsa.mutsa_week5_hw.global.code.ProductErrorCode;
import mutsa.mutsa_week5_hw.global.exception.ProjectException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    public void decreaseStock(int quantity) {

        if (this.stock < quantity) {
            throw new ProjectException(
                    ProductErrorCode.OUT_OF_STOCK
            );
        }

        this.stock -= quantity;
    }
    public void increaseStock(int quantity) {
        this.stock += quantity;
    }
}
