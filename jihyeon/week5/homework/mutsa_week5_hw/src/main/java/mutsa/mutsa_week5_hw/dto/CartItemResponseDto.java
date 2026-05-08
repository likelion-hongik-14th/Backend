package mutsa.mutsa_week5_hw.dto;

import lombok.Getter;
import mutsa.mutsa_week5_hw.domain.CartItem;

@Getter
public class CartItemResponseDto {

    //장바구니 조회
    private Long itemId;
    private String productName;
    private int price;
    private int quantity;
    private int subtotal;

    public CartItemResponseDto(CartItem item) {
        this.itemId = item.getId();
        this.productName = item.getProduct().getName();
        this.price = item.getProduct().getPrice();
        this.quantity = item.getQuantity();
        this.subtotal = item.calculatePrice();
    }
}
