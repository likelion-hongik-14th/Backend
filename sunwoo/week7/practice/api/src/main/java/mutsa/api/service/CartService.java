package mutsa.api.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mutsa.api.domain.Cart;
import mutsa.api.domain.CartItem;
import mutsa.api.domain.Product;
import mutsa.api.domain.Users;
import mutsa.api.dto.CartItemRequestDto;
import mutsa.api.dto.CartItemUpdateDto;
import mutsa.api.dto.CartResponseDto;
import mutsa.api.global.apiPayload.exception.NotFoundException;
import mutsa.api.repository.CartItemRepository;
import mutsa.api.repository.CartRepository;
import mutsa.api.repository.ProductRepository;
import mutsa.api.repository.UsersRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;

    private Cart getCart(Long userId) {
        Users user = getUsers(userId);
        return cartRepository.findByUsersId(userId)
                .orElseGet(() -> cartRepository.save(Cart.createCart(user)));
    }

    //TODO: 장바구니에 상품 추가
    @Transactional
    public void addCart(Long userId, CartItemRequestDto requestDto) {
        Cart cart = getCart(userId);
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + requestDto.getProductId()));

        cart.getCartItems().stream()
                .filter(cartItem ->
                        cartItem.getProduct().getId().equals(requestDto.getProductId()))
                .findFirst()
                .ifPresentOrElse(cartItem -> {
                    int totalQuantity = cartItem.getQuantity() + requestDto.getQuantity();
                    if (totalQuantity > product.getStock()) {
                        throw new IllegalArgumentException("Requested quantity exceeds available stock. Available stock: " + product.getStock());
                    }
                    cartItem.increaseQuantity(requestDto.getQuantity());
                    cartItemRepository.save(cartItem);
                }, () -> {
                    if (requestDto.getQuantity() > product.getStock()) {
                        throw new IllegalArgumentException("Requested quantity exceeds available stock. Available stock: " + product.getStock());
                    }
                    cart.addProduct(product, requestDto.getQuantity());
                    cartRepository.save(cart);
                });
    }

    //TODO: 장바구니 전체 조회
    @Transactional
    public CartResponseDto getAllCart(Long userId) {
        Cart cart = getCart(userId);
        //카트의 모든 상품과 수량을 조회하여 CartResponseDto로 변환
        return CartResponseDto.from(cart);
    }

    //TODO: 장바구니에서 상품 수량 변경
    @Transactional
    public void updateCartItemQuantity(Long userId, Long cartItemId, CartItemUpdateDto updateDto) {
        CartItem cartItem = cartItemRepository.findByIdAndCartUsersId(cartItemId, userId)
                .orElseThrow(() -> new NotFoundException("CartItem not found with id: " + cartItemId));

        if (updateDto.getQuantity() > cartItem.getProduct().getStock()) {
            throw new IllegalArgumentException("Requested quantity exceeds available stock. Available stock: " + cartItem.getProduct().getStock());
        }
        cartItem.updateQuantity(updateDto.getQuantity());
        cartItemRepository.save(cartItem);
    }

    //TODO: 장바구니에서 상품 삭제
    @Transactional
    public void deleteCartItem(Long userId, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findByIdAndCartUsersId(cartItemId, userId)
                .orElseThrow(() -> new NotFoundException("CartItem not found with id: " + cartItemId));
        cartItemRepository.delete(cartItem);
    }

    //TODO: 장바구니에서 전체 상품 삭제
    @Transactional
    public void initCart(Long userId) {
        Cart cart = getCart(userId);
        cart.getCartItems().clear();
    }

    private Users getUsers(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
    }

}
