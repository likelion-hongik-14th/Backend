package com.app.mutsa_shoppingmall.service;

import com.app.mutsa_shoppingmall.dto.CartDto;
import com.app.mutsa_shoppingmall.entity.Cart;
import com.app.mutsa_shoppingmall.entity.CartItem;
import com.app.mutsa_shoppingmall.entity.Product;
import com.app.mutsa_shoppingmall.exception.ProjectException;
import com.app.mutsa_shoppingmall.exception.code.CartErrorCode;
import com.app.mutsa_shoppingmall.exception.code.CartItemErrorCode;
import com.app.mutsa_shoppingmall.exception.code.ProductErrorCode;
import com.app.mutsa_shoppingmall.repository.CartItemRepository;
import com.app.mutsa_shoppingmall.repository.CartRepository;
import com.app.mutsa_shoppingmall.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Cart getDefaultCart() {
        return cartRepository.findById(1L)
                .orElseGet(() -> cartRepository.save(Cart.builder().build()));
    }

    public CartDto.CartResponse getCart() {
        Cart cart = cartRepository.findById(1L)
                .orElseThrow(() -> new ProjectException(CartErrorCode.CART_NOT_FOUND));

        List<CartItem> items = cart.getCartItems();
        List<CartDto.CartItemDetail> details = items.stream()
                .map(CartDto.CartItemDetail::from)
                .toList();

        int totalPrice = items.stream()
                .mapToInt(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        return new CartDto.CartResponse(details, totalPrice);
    }

    @Transactional
    public CartDto.ItemResponse addCartItem(CartDto.ItemRequest request) {
        Cart cart = getDefaultCart();
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(request.getQuantity())
                .color(request.getColor())
                .build();

        CartItem savedItem = cartItemRepository.save(cartItem);

        return new CartDto.ItemResponse(
                savedItem.getId(),
                product.getId(),
                savedItem.getQuantity(),
                savedItem.getColor()
        );
    }

    @Transactional
    public CartDto.ItemResponse updateCartItem(Long itemId, CartDto.ItemRequest request) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ProjectException(CartItemErrorCode.CART_ITEM_NOT_FOUND));

        cartItem.update(request.getQuantity(), request.getColor());

        return new CartDto.ItemResponse(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getQuantity(),
                cartItem.getColor()
        );
    }

    @Transactional
    public void deleteCartItem(Long itemId) {
        if (!cartItemRepository.existsById(itemId)) {
            throw new ProjectException(CartItemErrorCode.CART_ITEM_NOT_FOUND);
        }
        cartItemRepository.deleteById(itemId);
    }
}