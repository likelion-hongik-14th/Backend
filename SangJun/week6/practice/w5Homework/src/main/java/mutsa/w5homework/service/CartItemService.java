package mutsa.w5homework.service;

import lombok.RequiredArgsConstructor;
import mutsa.w5homework.domain.Cart;
import mutsa.w5homework.domain.CartItem;
import mutsa.w5homework.domain.Product;
import mutsa.w5homework.dto.CartItemCreateRequestDto;
import mutsa.w5homework.dto.CartItemResponseDto;
import mutsa.w5homework.repository.CartItemRepository;
import mutsa.w5homework.repository.CartRepository;
import mutsa.w5homework.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Transactional
    public CartItemResponseDto createCartItem(CartItemCreateRequestDto requestDto) {
        if (requestDto.getCount() <= 0 || requestDto.getProductId() == null) {
            throw new IllegalArgumentException("Invalid request");
        }

        Cart cart = cartRepository.findById(requestDto.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if(product.getStock() < requestDto.getCount()) {
            throw new IllegalArgumentException("Not enough stock");
        }

        CartItem cartItem = CartItem.builder()
                .count(requestDto.getCount())
                .product(product)
                .cart(cart)
                .build();
        CartItem cartItemSaved = cartItemRepository.save(cartItem);
        return new  CartItemResponseDto(cartItemSaved);
    }
}
