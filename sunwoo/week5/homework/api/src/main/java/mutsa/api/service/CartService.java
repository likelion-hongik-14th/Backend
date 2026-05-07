package mutsa.api.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mutsa.api.domain.Cart;
import mutsa.api.domain.CartItem;
import mutsa.api.domain.Product;
import mutsa.api.dto.CartItemRequestDto;
import mutsa.api.dto.CartItemUpdateDto;
import mutsa.api.dto.CartResponseDto;
import mutsa.api.repository.CartItemRepository;
import mutsa.api.repository.CartRepository;
import mutsa.api.repository.ProductRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    private Cart getCart() {
        return cartRepository.findById(1L).orElseGet(() -> {
            return cartRepository.save(Cart.createCart());
        });
    }

    //TODO: 장바구니에 상품 추가
    @Transactional
    public void addCart(CartItemRequestDto requestDto) {
        Cart cart = getCart();
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + requestDto.getProductId()));
        cart.getCartItems().stream().filter(cartItem -> cartItem.getProduct().getId().equals(requestDto.getProductId())).findFirst().ifPresentOrElse(cartItem -> {
            cartItem.increaseQuantity(requestDto.getQuantity());
            cartItemRepository.save(cartItem);
        }, () -> {
            cart.addProduct(product, requestDto.getQuantity());
            cartRepository.save(cart);
        });


    }

    //TODO: 장바구니 전체 조회
    @Transactional
    public CartResponseDto getAllCart() {
        Cart cart = getCart();
        //카트의 모든 상품과 수량을 조회하여 CartResponseDto로 변환
        return CartResponseDto.from(cart);
    }

    //TODO: 장바구니에서 상품 수량 변경
    @Transactional
    public void updateCartItemQuantity(Long cartItemId, CartItemUpdateDto updateDto) {
        Cart cart = getCart();
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new IllegalArgumentException("CartItem not found with id: " + cartItemId));
        cartItem.updateQuantity(updateDto.getQuantity());
        cartItemRepository.save(cartItem);
    }

    //TODO: 장바구니에서 상품 삭제
    @Transactional
    public void deleteCartItem(Long cartItemId) {
        Cart cart = getCart();
        cartItemRepository.findById(cartItemId).orElseThrow(() -> new IllegalArgumentException("CartItem not found with id: " + cartItemId));
        cartItemRepository.deleteById(cartItemId);
    }

    //TODO: 장바구니에서 전체 상품 삭제
    @Transactional
    public void initCart() {
        Cart cart = getCart();
        cart.getCartItems().clear();
    }

}
