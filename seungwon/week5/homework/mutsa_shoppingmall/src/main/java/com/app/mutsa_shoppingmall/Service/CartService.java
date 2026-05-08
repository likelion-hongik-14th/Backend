package com.app.mutsa_shoppingmall.Service;

import com.app.mutsa_shoppingmall.DTO.CartDto;
import com.app.mutsa_shoppingmall.Entity.Cart;
import com.app.mutsa_shoppingmall.Entity.CartItem;
import com.app.mutsa_shoppingmall.Entity.Product;
import com.app.mutsa_shoppingmall.Repository.CartItemRepository;
import com.app.mutsa_shoppingmall.Repository.CartRepository;
import com.app.mutsa_shoppingmall.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    // 공통 로직: 현재 사용자의 장바구니 획득 (인증이 없으므로 ID 1번을 강제 사용)
    public Cart getDefaultCart() {
        return cartRepository.findById(1L)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder().build();
                    return cartRepository.save(newCart); // 없으면 새로 만들어서 저장 후 반환
                });
    }

    // 1. 장바구니 조회
    public CartDto.CartResponse getCart() {
        Cart cart = getDefaultCart();
        List<CartItem> items = cart.getCartItems();

        List<CartDto.CartItemDetail> details = items.stream()
                .map(item -> new CartDto.CartItemDetail(
                        item.getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity(),
                        item.getProduct().getStock()
                )).collect(Collectors.toList());

        int totalPrice = items.stream()
                .mapToInt(item -> (int) (item.getProduct().getPrice() * item.getQuantity()))
                .sum();

        return new CartDto.CartResponse(details, totalPrice);
    }

    // 2. 장바구니 상품 추가
    @Transactional
    public CartDto.ItemResponse addCartItem(CartDto.ItemRequest request) {
        Cart cart = getDefaultCart();
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

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

    // 3. 장바구니 상품 수량 변경
    @Transactional
    public CartDto.ItemResponse updateCartItem(Long itemId, CartDto.ItemRequest request) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 아이템이 존재하지 않습니다."));

        // 변경 감지(Dirty Checking) 작동
        cartItem.update(request.getQuantity(), request.getColor());

        return new CartDto.ItemResponse(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getQuantity(),
                cartItem.getColor()
        );
    }

    // 4. 장바구니 상품 삭제
    @Transactional
    public void deleteCartItem(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }
}
