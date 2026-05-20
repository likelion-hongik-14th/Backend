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

    @Min(1)
    private int itemQuantity;

    // DTO → Entity 변환
    // DTO에서 변환을 통해 서비스 코드가 갈끔해짐
    public CartItem toEntity(Cart cart, Product product) {
        return CartItem.create(cart, product, this.itemQuantity);
    }
}