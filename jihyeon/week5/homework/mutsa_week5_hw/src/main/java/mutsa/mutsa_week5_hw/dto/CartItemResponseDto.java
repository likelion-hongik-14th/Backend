package mutsa.mutsa_week5_hw.dto;

import lombok.Builder;
import lombok.Getter;
import mutsa.mutsa_week5_hw.domain.CartItem;

@Getter
@Builder // 수정: 안전하고 읽기 쉬운 DTO 객체 생성 방식으로의 변경을 위해 @Builder 추가
public class CartItemResponseDto {

    //장바구니 조회
    private Long itemId;
    private String productName;
    private int price;
    private int quantity;
    private int subtotal;

    public static CartItemResponseDto from(CartItem item) { // 수정: 엔티티를 DTO로 변환하는 역할을 명확히 표현하기 위해 static from()으로 변경
        return CartItemResponseDto.builder()
                .itemId(item.getId())
                .productName(item.getProduct().getName())
                .price(item.getProduct().getPrice())
                .quantity(item.getQuantity())
                .subtotal(item.calculatePrice())
                .build();
    }
}
