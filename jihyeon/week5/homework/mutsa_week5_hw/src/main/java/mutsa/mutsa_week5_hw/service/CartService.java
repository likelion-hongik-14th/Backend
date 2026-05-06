package mutsa.mutsa_week5_hw.service;

import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.domain.Cart;
import mutsa.mutsa_week5_hw.domain.CartItem;
import mutsa.mutsa_week5_hw.domain.Product;
import mutsa.mutsa_week5_hw.dto.CartItemRequestDto;
import mutsa.mutsa_week5_hw.dto.CartItemUpdateDto;
import mutsa.mutsa_week5_hw.dto.CartResponseDto;
import mutsa.mutsa_week5_hw.repository.CartItemRepository;
import mutsa.mutsa_week5_hw.repository.CartRepository;
import mutsa.mutsa_week5_hw.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    // 장바구니 조회
    public CartResponseDto getCart() {
        Cart cart = cartRepository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> cartRepository.save(Cart.createCart()));

        return new CartResponseDto(cart);
    }

    // 상품 추가
    @Transactional
    public CartResponseDto addItem(CartItemRequestDto requestDto) {

        Cart cart = cartRepository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> cartRepository.save(Cart.createCart()));

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

        product.decreaseStock(requestDto.getQuantity());
        productRepository.save(product);

        cart.addProduct(product, requestDto.getQuantity());
        cartRepository.save(cart);

        return new CartResponseDto(cart);
    }

    // 수량 변경
    @Transactional
    public CartResponseDto updateItemQuantity(Long itemId, CartItemUpdateDto requestDto) {

        Cart cart = cartRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow();

        CartItem item = cart.getCartItems().stream()
                .filter(ci -> ci.getId().equals(itemId))
                .findFirst()
                .orElseThrow();

        item.increaseQuantity(requestDto.getQuantity());

        return new CartResponseDto(cart);
    }

    // 삭제
    @Transactional
    public void deleteItem(Long itemId) {

        Cart cart = cartRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow();

        cart.getCartItems().removeIf(ci -> ci.getId().equals(itemId));
    }
}
