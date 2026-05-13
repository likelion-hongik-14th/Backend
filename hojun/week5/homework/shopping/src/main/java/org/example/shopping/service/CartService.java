package org.example.shopping.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.domain.Cart;
import org.example.shopping.domain.CartItem;
import org.example.shopping.domain.Product;
import org.example.shopping.domain.User;
import org.example.shopping.dto.CartItemRequestDto;
import org.example.shopping.dto.CartItemResponseDto;
import org.example.shopping.dto.CartItemUpdateDto;
import org.example.shopping.dto.CartResponseDto;
import org.example.shopping.repository.CartRepository;
import org.example.shopping.repository.CartItemRepository;
import org.example.shopping.repository.ProductRepository;
import org.example.shopping.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void addCartItem(Long userId, CartItemRequestDto request){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("사용자가 없습니다"));

        Cart cart = user.getCart();

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(()-> new IllegalArgumentException("상품이 없습니다."));

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if(existingItem != null){
            existingItem.addQuantity(request.getQuantity());
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();
        }
    }

    @Transactional(readOnly = true)
    public CartResponseDto getCart(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        Cart cart = user.getCart();

        List<CartItemResponseDto> items = cart.getCartItems().stream()
                .map(cartItem -> CartItemResponseDto.builder()
                        .productId(cartItem.getProduct().getId())
                        .productName(cartItem.getProduct().getName())
                        .quantity(cartItem.getQuantity())
                        .unitPrice(cartItem.getProduct().getPrice())
                        .totalPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity())
                        .build())
                .toList();

        Long totalOrderPrice = items.stream()
                .mapToLong(CartItemResponseDto::getTotalPrice)
                .sum();

        return CartResponseDto.builder()
                .items(items)
                .totalOrderPrice(totalOrderPrice)
                .build();
    }

    @Transactional
    public void updateCartItem(Long userId, Long cartItemId, CartItemUpdateDto request){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 사용자입니다"));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));

        cartItem.updateQuantity(request.getQuantity());
    }

    @Transactional
    public void deleteCartItem(Long userId, Long cartItemId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(()-> new IllegalArgumentException("사용자가 존재하지 않습니다."));
            Cart cart = user.getCart();

            CartItem targetItem = cartItemRepository.findById(cartItemId)
                    .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 상품입니다."));
            cart.getCartItems().remove(targetItem);
    }
}
