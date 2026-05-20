package mutsa.hw5.service;

import lombok.RequiredArgsConstructor;
import mutsa.hw5.domain.Cart;
import mutsa.hw5.domain.CartItem;
import mutsa.hw5.domain.Product;
import mutsa.hw5.dto.cart.CartResponseDto;
import mutsa.hw5.dto.cartitem.CartItemRequestDto;
import mutsa.hw5.dto.cartitem.CartItemResponseDto;
import mutsa.hw5.dto.cartitem.CartItemUpdateDto;
import mutsa.hw5.repository.CartItemRepository;
import mutsa.hw5.repository.CartRepository;
import mutsa.hw5.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // JPA에게 이 클래스가 서비스 클래스라는 걸 명시
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    // 장바구니 조회
    @Transactional(readOnly = true)
    public CartResponseDto getCart(Long memberId) {
        Cart cart = cartRepository.findByMemberIdWithItems(memberId)
                .orElseThrow(() -> new RuntimeException("장바구니를 찾을 수 없습니다."));
        return CartResponseDto.from(cart);
    }

    // 장바구니 상품 담기
    @Transactional
    public CartItemResponseDto addCartItem(Long memberId, CartItemRequestDto dto) {
        Cart cart = cartRepository.findByMember_MemberId(memberId)
                .orElseThrow(() -> new RuntimeException("장바구니를 찾을 수 없습니다."));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

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
    public CartItemResponseDto updateCartItem(Long itemId, CartItemUpdateDto dto) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("장바구니 아이템을 찾을 수 없습니다."));
        cartItem.getProduct().checkStock(dto.getItemQuantity());
        cartItem.update(dto);
        return CartItemResponseDto.from(cartItem);
    }

    // 장바구니 상품 삭제
    @Transactional
    public void deleteCartItem(Long itemId) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("장바구니 아이템을 찾을 수 없습니다."));
        cartItemRepository.delete(cartItem);
    }
}