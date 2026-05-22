package mutsa.mutsa_week5_hw.service;

import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.domain.Cart;
import mutsa.mutsa_week5_hw.domain.CartItem;
import mutsa.mutsa_week5_hw.domain.Member;
import mutsa.mutsa_week5_hw.domain.Product;
import mutsa.mutsa_week5_hw.dto.CartItemRequestDto;
import mutsa.mutsa_week5_hw.dto.CartItemUpdateDto;
import mutsa.mutsa_week5_hw.dto.CartResponseDto;
import mutsa.mutsa_week5_hw.repository.CartRepository;
import mutsa.mutsa_week5_hw.repository.MemberRepository;
import mutsa.mutsa_week5_hw.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    // 장바구니 조회
    @Transactional(readOnly = true) // 수정: 조회 전용 메서드
    public CartResponseDto getCart() { // 수정: getOrCreateCart() 메서드로 빼기

        Cart cart = getOrCreateCart();

        return CartResponseDto.from(cart);
    }

    // 상품 추가
    @Transactional
    public CartResponseDto addItem(CartItemRequestDto requestDto) {

        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Cart cart = getOrCreateCart();

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

        product.decreaseStock(requestDto.getQuantity());

        cart.addProduct(product, requestDto.getQuantity());
        cartRepository.save(cart);

        return CartResponseDto.from(cart);
    }

    // 수량 변경
    @Transactional
    public CartResponseDto updateItemQuantity(Long itemId, CartItemUpdateDto requestDto) {

        Cart cart = getOrCreateCart();

        CartItem item = cart.getCartItems().stream()
                .filter(ci -> ci.getId().equals(itemId))
                .findFirst()
                .orElseThrow();

        item.increaseQuantity(requestDto.getQuantity());

        return CartResponseDto.from(cart);
    }

    // 삭제
    @Transactional
    public void deleteItem(Long itemId) {

        Cart cart = getOrCreateCart();

        cart.getCartItems().removeIf(ci -> ci.getId().equals(itemId));
    }

    // 수정: 장바구니 조회 또는 생성하는 getOrCreateCart() 메서드 추가
    private Cart getOrCreateCart() {

        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        return cartRepository.findFirstBy()
                .orElseGet(() ->
                        cartRepository.save(Cart.createCart(member)));
    }
}
