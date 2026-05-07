package mutsa.session5.Service;

import mutsa.session5.Dto.CartItemRequestDto;
import mutsa.session5.Dto.CartItemResponseDto;
import mutsa.session5.Dto.CartResponseDto;
import mutsa.session5.Entity.Cart;
import mutsa.session5.Entity.CartItem;
import mutsa.session5.Entity.Product;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import mutsa.session5.Repository.CartItemRepository;
import mutsa.session5.Repository.CartRepository;
import mutsa.session5.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    // 장바구니 상품 담기
    @Transactional
    public CartItemResponseDto addCartItem(CartItemRequestDto requestDto) {
        Product product = productRepository.findById(requestDto.getProduct_id())
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        // 장바구니 조회 및 생성
        Cart cart = cartRepository.findByUserId(requestDto.getUserId())
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .userId(requestDto.getUserId())
                        .build()));

        // 재고 확인
        int currentQuantity = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProduct_id().equals(requestDto.getProduct_id()))
                .mapToInt(CartItem::getQuantity)
                .sum();
        int totalQuantity = currentQuantity + requestDto.getQuantity();

        if (totalQuantity <= 0) {
            throw new IllegalArgumentException(("장바구니 수량은 최소 1개 이상이어야 합니다."));
        }
        if (totalQuantity > product.getStock()) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        // 기존 상품 중복 확인
        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(
                cart.getCart_id(), product.getProduct_id());

        CartItem savedItem;
        if (existingItem.isPresent()) {
            savedItem = existingItem.get();
            savedItem.increaseQuantity(requestDto.getQuantity());
        }
        else {
            savedItem = cart.addProduct(product, requestDto.getQuantity());
            cartItemRepository.save(savedItem);
        }

        return CartItemResponseDto.builder()
                .cart_id(savedItem.getCart().getCart_id())
                .product_id(savedItem.getProduct().getProduct_id())
                .quantity(savedItem.getQuantity())
                .build();
    }

    // 장바구니 상품 조회
    @Transactional
    public CartResponseDto getCartResponseDto(Long user_id) {
        Cart cart = cartRepository.findByUserId(user_id)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 비어있습니다."));

        List<CartItemResponseDto> items = cart.getCartItems().stream()
                .map(item -> CartItemResponseDto.builder()
                        .cart_id(cart.getCart_id())
                        .name(item.getProduct().getName())
                        .price(item.getProduct().getPrice())
                        .quantity(item.getQuantity())
                        .build())
                .toList();

        return CartResponseDto.builder()
                .cartItems(items)
                .totalPrice(cart.calculateTotalPrice())
                .build();
    }

    // 장바구니 상품 수량 변경
    @Transactional
    public CartItemResponseDto updateCartItemQuantity(Long cart_item_id, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cart_item_id)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 아이템을 찾을 수 없습니다."));

        // 재고 확인
        if (cartItem.getProduct().getStock() < quantity) {
            throw new IllegalArgumentException("재고보다 많은 수량을 설정할 수 없습니다.");
        }

        cartItem.updateQuantity(quantity);

        return CartItemResponseDto.builder()
                .cart_id(cartItem.getCart().getCart_id())
                .product_id(cartItem.getProduct().getProduct_id())
                .quantity(cartItem.getQuantity())
                .build();
    }

    // 장바구니 상품 삭제
    @Transactional
    public void deleteCartItem(Long cart_item_id) {
        if (!cartItemRepository.existsById(cart_item_id)) {
            throw new IllegalArgumentException("삭제할 아이템이 존재하지 않습니다.");
        }
        cartItemRepository.deleteById(cart_item_id);
    }
}