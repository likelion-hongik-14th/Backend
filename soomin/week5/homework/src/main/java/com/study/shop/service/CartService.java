package com.study.shop.service;

import com.study.shop.domain.Cart;
import com.study.shop.domain.CartItem;
import com.study.shop.domain.Product;
import com.study.shop.dto.cart.AddCartItemRequest;
import com.study.shop.dto.cart.CartItemResponse;
import com.study.shop.dto.cart.CartResponse;
import com.study.shop.dto.cart.UpdateCartItemRequest;
import com.study.shop.repository.CartItemRepository;
import com.study.shop.repository.CartRepository;
import com.study.shop.repository.MemberRepository;
import com.study.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public CartResponse getCart(Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id=" + memberId));

        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니를 찾을 수 없습니다. memberId=" + memberId));

        List<CartItemResponse> items = cartItemRepository.findAllByCartId(cart.getId())
                .stream()
                .map(CartItemResponse::new)
                .toList();

        int totalPrice = items.stream()
                .mapToInt(CartItemResponse::getItemTotalPrice)
                .sum();

        return new CartResponse(cart.getId(), memberId, items, totalPrice);

    }

    @Transactional
    public CartItemResponse addCartItem(Long memberId, AddCartItemRequest request) {

        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니를 찾을 수 없습니다. memberId=" + memberId));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + request.getProductId()));

        if (product.getStockQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException("상품 재고가 부족합니다.");
        }

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .map(existingItem -> {
                    int newQuantity = existingItem.getQuantity() + request.getQuantity();

                    if (product.getStockQuantity() < newQuantity) {
                        throw new IllegalArgumentException("상품 재고가 부족합니다.");
                    }

                    existingItem.addQuantity(request.getQuantity());
                    return existingItem;
                })
                .orElseGet(() -> {
                    CartItem newItem = new CartItem(cart, product, request.getQuantity());
                    return cartItemRepository.save(newItem);
                });

        return new CartItemResponse(cartItem);

    }

    @Transactional
    public CartItemResponse updateCartItem(Long cartItemId, Long memberId, UpdateCartItemRequest request) {
        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니를 찾을 수 없습니다. id=" + memberId));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 상품을 찾을 수 없습니다. id=" + cartItemId));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("다른 회원의 장바구니는 수정할 수 없습니다.");
        }

        if (cartItem.getProduct().getStockQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException("상품 재고가 부족합니다.");
        }

        cartItem.updateQuantity(request.getQuantity());

        return new CartItemResponse(cartItem);

    }

    @Transactional
    public void deleteCartItem(Long memberId, Long cartItemId) {

        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니를 찾을 수 없습니다. id=" + memberId));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 상품을 찾을 수 없습니다. id=" + cartItemId));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("다른 회원의 장바구니 상품은 삭제할 수 없습니다.");
        }

        cartItemRepository.delete(cartItem);

    }

}
