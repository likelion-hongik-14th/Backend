package mutsa.week2.cart;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mutsa.week2.common.error.BusinessException;
import mutsa.week2.common.error.ErrorCode;
import mutsa.week2.product.Product;
import mutsa.week2.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CartItemResponseDto addItem(CartAddItemRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        CartItem cartItem = CartItem.builder()
                .product(product)
                .quantity(requestDto.getQuantity())
                .build();

        return CartItemResponseDto.from(cartItemRepository.save(cartItem));
    }

    public CartResponseDto getCart() {
        List<CartItemResponseDto> cartItems = cartItemRepository.findAll().stream()
                .map(CartItemResponseDto::from)
                .toList();
        return CartResponseDto.of(cartItems);
    }

    @Transactional
    public CartItemResponseDto updateQuantity(Long itemId, CartUpdateQuantityRequestDto requestDto) {
        CartItem cartItem = findCartItem(itemId);
        cartItem.updateQuantity(requestDto.getQuantity());
        return CartItemResponseDto.from(cartItem);
    }

    @Transactional
    public void deleteItem(Long itemId) {
        CartItem cartItem = findCartItem(itemId);
        cartItemRepository.delete(cartItem);
    }

    @Transactional
    public CartItemResponseDto updateImage(Long itemId, CartUpdateImageRequestDto requestDto) {
        CartItem cartItem = findCartItem(itemId);
        cartItem.updateImageUrl(requestDto.getImageUrl());
        return CartItemResponseDto.from(cartItem);
    }

    private CartItem findCartItem(Long itemId) {
        return cartItemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
    }
}
