package com.app.mutsa_shoppingmall.Service;

import com.app.mutsa_shoppingmall.DTO.CartDto;
import com.app.mutsa_shoppingmall.Entity.Cart;
import com.app.mutsa_shoppingmall.Entity.CartItem;
import com.app.mutsa_shoppingmall.Entity.Product;
import com.app.mutsa_shoppingmall.Repository.CartItemRepository;
import com.app.mutsa_shoppingmall.Repository.CartRepository;
import com.app.mutsa_shoppingmall.Repository.ProductRepository;
import com.app.mutsa_shoppingmall.exception.ErrorCode;
import com.app.mutsa_shoppingmall.exception.GeneralException;
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
                .orElseGet(() -> {
                    Cart newCart = Cart.builder().build();
                    return cartRepository.save(newCart);
                });
    }

    public CartDto.CartResponse getCart() {
        Cart cart = getDefaultCart();
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
                .orElseThrow(() -> new GeneralException(ErrorCode.PRODUCT_NOT_FOUND));

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
                .orElseThrow(() -> new GeneralException(ErrorCode.CART_ITEM_NOT_FOUND));

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
        cartItemRepository.deleteById(itemId);
    }
}