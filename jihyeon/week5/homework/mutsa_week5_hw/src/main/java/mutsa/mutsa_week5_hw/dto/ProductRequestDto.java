package mutsa.mutsa_week5_hw.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequestDto {

    //상품 생성
    private String name;
    private int price;
    private int stock;
}
