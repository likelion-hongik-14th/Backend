package org.example.shopping.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.domain.Cart;
import org.example.shopping.domain.CartItem;
import org.example.shopping.domain.Product;
import org.example.shopping.domain.User;
import org.example.shopping.dto.cart.*;
import org.example.shopping.global.apiPayload.code.domain.CartErrorCode;
import org.example.shopping.global.apiPayload.code.domain.CartItemErrorCode;
import org.example.shopping.global.apiPayload.code.domain.ProductErrorCode;
import org.example.shopping.global.apiPayload.code.domain.UserErrorCode;
import org.example.shopping.global.apiPayload.exception.ProjectException;
import org.example.shopping.repository.CartItemRepository;
import org.example.shopping.repository.ProductRepository;
import org.example.shopping.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class CartService {
        private final CartItemRepository cartItemRepository;
        private final UserRepository userRepository;
        private final ProductRepository productRepository;

        @Transactional
        public CartItemResponseDto addCartItem(Long userId, CartItemRequestDto request){
            User user = userRepository.findById(userId)
                    .orElseThrow(()-> new ProjectException(UserErrorCode.USER_NOT_FOUND));

            Cart cart = user.getCart();
            if(cart == null){
                throw new ProjectException(CartErrorCode.CART_NOT_FOUND);
            }

            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(()-> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));

            if(product.getStock() < request.getQuantity()){
                throw new ProjectException(ProductErrorCode.OUT_OF_STOCK);
            }
            CartItem cartItem = cart.addCartItem(product, request.getQuantity());

            cartItemRepository.save(cartItem);

            return CartItemResponseDto.builder()
                    .productId(product.getId())
                    .itemId(cartItem.getId())
                    .productName(product.getName())
                    .description(product.getDescription())
                    .quantity(cartItem.getQuantity())
                    .unitPrice(product.getPrice())
                    .totalPrice(cartItem.getTotalPrice())
                    .build();
        }

    @Transactional(readOnly = true)
    public CartResponseDto getCart(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        Cart cart = user.getCart();

        List<CartItemResponseDto> items = cart.getCartItems().stream()
                .map(cartItem -> CartItemResponseDto.builder()
                        .productId(cartItem.getProduct().getId())
                        .itemId(cartItem.getProduct().getId())
                        .productName(cartItem.getProduct().getName())
                        .description(cartItem.getProduct().getDescription())
                        .quantity(cartItem.getQuantity())
                        .unitPrice(cartItem.getProduct().getPrice())
                        .totalPrice(cartItem.getTotalPrice())
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
                .orElseThrow(()-> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findByIdAndCartId(cartItemId, user.getCart().getId())
                .orElseThrow(()->new ProjectException(CartItemErrorCode.CART_ITEM_NOT_FOUND));

        cartItem.updateQuantity(request.getQuantity());
    }

    @Transactional
    public CartItemDeleteResponseDto deleteCartItem(Long userId, Long cartItemId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(()-> new ProjectException(UserErrorCode.USER_NOT_FOUND));
            Cart cart = user.getCart();

            CartItem targetItem = cartItemRepository.findByIdAndCartId(cartItemId, cart.getId())
                    .orElseThrow(()-> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));
            cart.getCartItems().remove(targetItem);
            return new CartItemDeleteResponseDto(targetItem.getProduct().getName(), targetItem.getQuantity());
    }
}
