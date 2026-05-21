package mutsa.hw5.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import mutsa.hw5.domain.Cart;
import mutsa.hw5.domain.CartItem;
import mutsa.hw5.domain.Product;

@Getter
public class CartItemRequestDto {
    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Long itemQuantity;

    // DTO → Entity 변환
    // Service에서 호출하지만, 변환 로직을 DTO에 숨겨두어서 깔끔하게 유지
    public CartItem toEntity(Cart cart, Product product) {
        return CartItem.create(cart, product, this.itemQuantity);
    }
}