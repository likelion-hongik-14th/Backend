package mutsa.api.service;

import lombok.RequiredArgsConstructor;
import mutsa.api.domain.Cart;
import mutsa.api.domain.CartItem;
import mutsa.api.domain.Product;
import mutsa.api.dto.CartItemRequestDto;
import mutsa.api.dto.CartItemResponseDto;
import mutsa.api.dto.CartItemUpdateDto;
import mutsa.api.dto.CartResponseDto;
import mutsa.api.repository.CartItemRepository;
import mutsa.api.repository.CartRepository;
import mutsa.api.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    private Cart getTestCart(){
        return cartRepository.findById(1L).orElseGet(()->cartRepository.save(Cart.createEmptyCart()));
    }

    // 장바구니 상품 목록 조회
    @Transactional(readOnly = true)
    public CartResponseDto getCart(){
        Cart cart = getTestCart();
        return new CartResponseDto(cart);
    }

    // 장바구니에 상품 추가
    @Transactional
    public void addCartItem(CartItemRequestDto requestDto){
        Cart cart = getTestCart();
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(()->new IllegalArgumentException("상품을 찾을 수 없습니다."));

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item->item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()){
            existingItem.get().increaseQuantity(requestDto.getQuantity());
        } else{
            cart.addProduct(product, requestDto.getQuantity());
        }
    }

    // 장바구니 상품의 수량 변경
    @Transactional
    public void updateCartItemQuantity(Long cartItemId, CartItemUpdateDto updateDto){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(()->new IllegalArgumentException("장바구니가 비어있습니다."));

        cartItem.updateQuantity(updateDto.getQuantity());
    }

    // 장바구니의 특정 상품 삭제
    @Transactional
    public void removeCartItem(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(()->new IllegalArgumentException("장바구니가 비어있습니다."));
        cartItemRepository.delete(cartItem);
    }

    // 장바구니 전체 비우기
    @Transactional
    public void clearCart(){
        Cart cart = getTestCart();
        cart.getCartItems().clear();
    }
}
