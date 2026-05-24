package mutsa.api.service;

import lombok.RequiredArgsConstructor;
import mutsa.api.domain.Cart;
import mutsa.api.domain.CartItem;
import mutsa.api.domain.Product;
import mutsa.api.domain.User;
import mutsa.api.dto.CartItemRequestDto;
import mutsa.api.dto.CartItemUpdateDto;
import mutsa.api.dto.CartResponseDto;
import mutsa.api.global.apiPayload.code.CartErrorCode;
import mutsa.api.global.apiPayload.code.ProductErrorCode;
import mutsa.api.global.apiPayload.code.UserErrorCode;
import mutsa.api.global.apiPayload.exception.ProjectException;
import mutsa.api.repository.CartItemRepository;
import mutsa.api.repository.CartRepository;
import mutsa.api.repository.ProductRepository;
import mutsa.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // [공통] 테스트용 유저 가져오기
    private User getTestUser() {
        return userRepository.findById(1L)
                .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));
    }

    // [공통] 내 장바구니 가져오기
    private Cart getTestCart(){
        User user = getTestUser();
        return cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(Cart.createEmptyCart(user)));
    }

    // [조회] 장바구니 상품 목록 조회
    public CartResponseDto getCart(){
        Cart cart = getTestCart();
        return CartResponseDto.of(cart);
    }

    // [추가] 장바구니에 상품 담기
    @Transactional
    public void addCartItem(CartItemRequestDto requestDto){
        Cart cart = getTestCart();
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(()-> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));

        cart.addProduct(product, requestDto.getQuantity());
    }

    // [수정] 장바구니 상품의 수량만 변경
    @Transactional
    public void updateCartItemQuantity(Long cartItemId, CartItemUpdateDto updateDto){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(()-> new ProjectException(CartErrorCode.CART_ITEM_NOT_FOUND));

        cartItem.updateQuantity(updateDto.getQuantity());
    }

    // [삭제] 장바구니 특정 상품 삭제
    @Transactional
    public void removeCartItem(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(()-> new ProjectException(CartErrorCode.CART_ITEM_NOT_FOUND));

        cartItemRepository.delete(cartItem);
    }

    // [삭제] 장바구니 전체 비우기
    @Transactional
    public void clearCart(){
        Cart cart = getTestCart();
        cart.clearCart();
    }
}