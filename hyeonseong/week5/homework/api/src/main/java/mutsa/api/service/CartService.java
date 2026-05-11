package mutsa.api.service;

import lombok.RequiredArgsConstructor;
import mutsa.api.domain.Cart;
import mutsa.api.domain.CartItem;
import mutsa.api.domain.Product;
import mutsa.api.domain.User;
import mutsa.api.dto.CartItemRequestDto;
import mutsa.api.dto.CartItemResponseDto;
import mutsa.api.dto.CartItemUpdateDto;
import mutsa.api.dto.CartResponseDto;
import mutsa.api.repository.CartItemRepository;
import mutsa.api.repository.CartRepository;
import mutsa.api.repository.ProductRepository;
import mutsa.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // [공통] 테스트용 유저 가져오기
    private User getTestUser() {
        return userRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));
    }

    // [공통] 내 장바구니 가져오기 (없으면 새로 만들어줌)
    private Cart getTestCart(){
        User user = getTestUser();
        return cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(Cart.createEmptyCart(user)));
    }

    // [조회] 장바구니 상품 목록 조회
    @Transactional(readOnly = true)
    public CartResponseDto getCart(){
        Cart cart = getTestCart();
        return new CartResponseDto(cart);
    }

    // [추가] 장바구니에 상품 담기
    @Transactional
    public void addCartItem(CartItemRequestDto requestDto){
        Cart cart = getTestCart();
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(()->new IllegalArgumentException("담으려는 상품을 찾을 수 없습니다."));

        cart.addProduct(product, requestDto.getQuantity());
    }

    // [수정] 장바구니 상품의 수량 변경
    @Transactional
    public void updateCartItemQuantity(Long cartItemId, CartItemUpdateDto updateDto){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(()->new IllegalArgumentException("수량 변경할 상품을 장바구니에서 찾을 수 없습니다."));

        cartItem.updateQuantity(updateDto.getQuantity());
    }

    // [삭제] 장바구니의 특정 상품 삭제
    @Transactional
    public void removeCartItem(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(()->new IllegalArgumentException("삭제할 상품을 장바구니에서 찾을 수 없습니다."));

        cartItemRepository.delete(cartItem);
    }

    // [삭제] 장바구니 전체 비우기
    @Transactional
    public void clearCart(){
        Cart cart = getTestCart();
        cart.clearCart();
    }
}
