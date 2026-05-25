package mutsa.hw5.service;

import lombok.RequiredArgsConstructor;
import mutsa.hw5.domain.Cart;
import mutsa.hw5.domain.CartItem;
import mutsa.hw5.domain.Product;
import mutsa.hw5.dto.cart.CartResponseDto;
import mutsa.hw5.dto.cartitem.CartItemRequestDto;
import mutsa.hw5.dto.cartitem.CartItemResponseDto;
import mutsa.hw5.dto.cartitem.CartItemUpdateDto;
import mutsa.hw5.global.apiPayload.code.CartErrorCode;
import mutsa.hw5.global.apiPayload.code.CartProductErrorCode;
import mutsa.hw5.global.apiPayload.code.ProductErrorCode;
import mutsa.hw5.global.apiPayload.exception.ProjectException;
import mutsa.hw5.repository.CartItemRepository;
import mutsa.hw5.repository.CartRepository;
import mutsa.hw5.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // JPA에게 이 클래스가 서비스 클래스라는 걸 명시

// final 필드들의 생성자를 자동 생성
// Spring이 이 생성자를 보고 Repository들을 자동으로 주입해줌 (의존성 주입)
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    // 장바구니 조회
    @Transactional(readOnly = true) // "readOnly = true"의 의미: DB를 조회만 하고 변경은 안 한다는 뜻
    public CartResponseDto getCart(Long memberId) {
        Cart cart = cartRepository.findByMemberIdWithItems(memberId)
                .orElseThrow(() -> new ProjectException(CartErrorCode.CART_NOT_FOUND));
        return CartResponseDto.from(cart);
    }

    // 장바구니 상품 담기
    @Transactional // SQLD에서 나오는 ACID 중에 그 원자성을 의미
    public CartItemResponseDto addCartItem(Long memberId, CartItemRequestDto dto) {
        Cart cart = cartRepository.findByMember_MemberId(memberId)
                .orElseThrow(() -> new ProjectException(CartErrorCode.CART_NOT_FOUND));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));

        CartItem cartItem = cartItemRepository
                .findByCart_CartIdAndProduct_ProductId(cart.getCartId(), dto.getProductId())
                .map(existing -> {
                    product.checkStock(existing.getItemQuantity() + dto.getItemQuantity());
                    existing.addQuantity(dto.getItemQuantity());
                    return existing;
                })
                .orElseGet(() -> {
                    product.checkStock(dto.getItemQuantity());
                    CartItem newItem = dto.toEntity(cart, product);
                    return cartItemRepository.save(newItem);
                });

        return CartItemResponseDto.from(cartItem);
    }

    // 장바구니 수량 변경
    @Transactional
    public CartItemResponseDto updateCartItem(Long memberId, Long itemId, CartItemUpdateDto dto) {
        Cart cart = cartRepository.findByMember_MemberId(memberId)
                .orElseThrow(() -> new ProjectException(CartErrorCode.CART_NOT_FOUND));
        CartItem cartItem = cartItemRepository.findByItemIdAndCart_CartId(itemId, cart.getCartId())
                .orElseThrow(() -> new ProjectException(CartProductErrorCode.CART_PRODUCT_NOT_FOUND));
        cartItem.getProduct().checkStock(dto.getItemQuantity());
        cartItem.update(dto);
        return CartItemResponseDto.from(cartItem);
    }

    // 장바구니 상품 삭제
    @Transactional
    public void deleteCartItem(Long memberId, Long itemId) {
        Cart cart = cartRepository.findByMember_MemberId(memberId)
                .orElseThrow(() -> new ProjectException(CartErrorCode.CART_NOT_FOUND));
        CartItem cartItem = cartItemRepository.findByItemIdAndCart_CartId(itemId, cart.getCartId())
                .orElseThrow(() -> new ProjectException(CartProductErrorCode.CART_PRODUCT_NOT_FOUND));
        cartItemRepository.delete(cartItem);
    }
}