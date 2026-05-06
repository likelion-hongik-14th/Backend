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

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(()-> cartRepository.save(new Cart(user)));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(()-> new IllegalArgumentException("상품이 없습니다."));

        CartItem existingCartItem = null;
        for(CartItem cartItem : cart.getCartItems()){
            if(cartItem.getProduct().getId().equals(request.getProductId())){
                existingCartItem = cartItem;
                break;
            }
        }

        if(existingCartItem == null){
            CartItem newCartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();
            cartItemRepository.save(newCartItem);
        } else {
            existingCartItem.addQuantity(request.getQuantity());
        }
        product.removeStock(request.getQuantity());
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
    public void updateCartItem(Long userId, Long productId, CartItemUpdateDto request){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 사용자입니다"));

        Cart cart = user.getCart();
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 상품입니다."));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(i->i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("장바구니에 해당 상품이 없습니다."));

        product.addStock(cartItem.getQuantity());
        product.removeStock(request.getQuantity());
        cartItem.updateQuantity(request.getQuantity());
    }

    @Transactional
    public void deleteCartItem(Long userId, Long productId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(()-> new IllegalArgumentException("사용자가 존재하지 않습니다."));
            Cart cart = user.getCart();

            Product product = productRepository.findById(productId)
                    .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 상품입니다."));

            CartItem targetItem = cart.getCartItems().stream()
                    .filter(i -> i.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElseThrow(()-> new IllegalArgumentException("상품이 장바구니에 없습니다."));

            product.addStock(targetItem.getQuantity());
            cart.getCartItems().remove(targetItem);
    }
}
