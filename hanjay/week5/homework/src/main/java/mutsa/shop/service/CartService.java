package mutsa.shop.service;

import lombok.RequiredArgsConstructor;
import mutsa.shop.domain.Cart;
import mutsa.shop.domain.CartItem;
import mutsa.shop.domain.Member;
import mutsa.shop.domain.Product;
import mutsa.shop.dto.CartItemAddRequestDto;
import mutsa.shop.dto.CartItemResponseDto;
import mutsa.shop.dto.CartItemUpdateRequestDto;
import mutsa.shop.dto.CartResponseDto;
import mutsa.shop.repository.CartItemRepository;
import mutsa.shop.repository.CartRepository;
import mutsa.shop.repository.MemberRepository;
import mutsa.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;

    //장바구니 상품 추가
    public CartItemResponseDto addItem(CartItemAddRequestDto requestDto, Long memberId) {
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseGet(() -> {
                    // memberId로 Member 객체를 먼저 찾아야 합니다.
                    Member member = memberRepository.findById(memberId)
                            .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
                    return cartRepository.save(new Cart(member)); // Long이 아닌 Member 객체 전달
                });

        CartItem cartItem = CartItem.builder()
                .quantity(requestDto.getQuantity())
                .product(product)
                .cart(cart)
                .build();
        CartItem savedItem = cartItemRepository.save(cartItem);
        return new CartItemResponseDto (savedItem);
    }

    //장바구니 조회
    @Transactional(readOnly = true)
    public CartResponseDto getCart(Long memberId) {
        Cart cart = cartRepository.findByMemberId(memberId).orElse(null);
        if (cart == null) {
            return CartResponseDto.builder()
                    .items(Collections.emptyList())
                    .totalPrice(0L)
                    .build();
        }

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        List<CartResponseDto.CartItemDto> itemDtos = cartItems.stream()
                .map(item -> CartResponseDto.CartItemDto.builder()
                        .cartItemId(item.getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .price(item.getProduct().getPrice())
                        .build())
                .toList(); // Java 16 이상

        // 4. 총합(Total Price) 계산
        long totalPrice = itemDtos.stream()
                .mapToLong(item -> item.getPrice() * item.getQuantity())
                .sum();

        // 5. 최종 응답 DTO 생성 및 반환
        return CartResponseDto.builder()
                .items(itemDtos)
                .totalPrice(totalPrice)
                .build();
    }
    //장바구니 상품 수량 변경
    @Transactional
    public CartItemResponseDto updateItemQuantity(Long cartItemId, CartItemUpdateRequestDto requestDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 장바구니 상품이 없습니다. id=" + cartItemId));

        cartItem.updateQuantity(requestDto.getQuantity());

        return new CartItemResponseDto(cartItem);
    }
    //장바구니 상품 삭제
    @Transactional
    public void deleteItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 장바구니 상품이 없습니다. id=" + cartItemId));

        cartItemRepository.delete(cartItem);
    }
}
