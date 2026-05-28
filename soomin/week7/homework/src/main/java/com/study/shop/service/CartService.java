package com.study.shop.service;

import com.study.shop.domain.Cart;
import com.study.shop.domain.CartItem;
import com.study.shop.domain.Product;
import com.study.shop.dto.cart.AddCartItemRequest;
import com.study.shop.dto.cart.CartItemResponse;
import com.study.shop.dto.cart.CartResponse;
import com.study.shop.dto.cart.UpdateCartItemRequest;
import com.study.shop.global.apiPayload.code.CartErrorCode;
import com.study.shop.global.apiPayload.code.MemberErrorCode;
import com.study.shop.global.apiPayload.code.ProductErrorCode;
import com.study.shop.global.apiPayload.exception.ProjectException;
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
                .orElseThrow(() -> new ProjectException(MemberErrorCode.MEMBER_NOT_FOUND));

        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new ProjectException(CartErrorCode.CART_NOT_FOUND));

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
                .orElseThrow(() -> new ProjectException(CartErrorCode.CART_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));

        if (product.getStockQuantity() < request.getQuantity()) {
            throw new ProjectException(ProductErrorCode.PRODUCT_STOCK_NOT_ENOUGH);
        }

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .map(existingItem -> {
                    int newQuantity = existingItem.getQuantity() + request.getQuantity();

                    if (product.getStockQuantity() < newQuantity) {
                        throw new ProjectException(ProductErrorCode.PRODUCT_STOCK_NOT_ENOUGH);
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
    public CartItemResponse updateCartItem(Long memberId, Long cartItemId, UpdateCartItemRequest request) {
        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new ProjectException(CartErrorCode.CART_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ProjectException(CartErrorCode.CART_ITEM_NOT_FOUND));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new ProjectException(CartErrorCode.CART_ACCESS_DENIED);
        }

        if (cartItem.getProduct().getStockQuantity() < request.getQuantity()) {
            throw new ProjectException(ProductErrorCode.PRODUCT_STOCK_NOT_ENOUGH);
        }

        cartItem.updateQuantity(request.getQuantity());

        return new CartItemResponse(cartItem);

    }

    @Transactional
    public void deleteCartItem(Long memberId, Long cartItemId) {

        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new ProjectException(CartErrorCode.CART_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ProjectException(CartErrorCode.CART_ITEM_NOT_FOUND));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new ProjectException(CartErrorCode.CART_ACCESS_DENIED);
        }

        cartItemRepository.delete(cartItem);

    }

}
