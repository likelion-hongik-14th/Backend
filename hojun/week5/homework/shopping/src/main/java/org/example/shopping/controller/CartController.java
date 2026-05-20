package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.CartItemRequestDto;
import org.example.shopping.dto.CartItemUpdateDto;
import org.example.shopping.dto.CartResponseDto;
import org.example.shopping.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/{userId}/items")
    public String addCartItem(@PathVariable Long userId, @RequestBody CartItemRequestDto request){

        cartService.addCartItem(userId, request);
        return "장바구니에 담겼습니다";
    }

    @GetMapping("/{userId}")
    public CartResponseDto getCart(@PathVariable Long userId){
        return cartService.getCart(userId);
    }

    @PatchMapping("/{userId}/items/{cartItemId}")
    public String updateCartItem(@PathVariable Long userId, @PathVariable Long cartItemId, @RequestBody CartItemUpdateDto request){
        cartService.updateCartItem(userId, cartItemId, request);
        return "수량이 수정되었습니다.";
    }

    @DeleteMapping("/{userId}/items/{cartItemId}")
    public String deleteCartItem(@PathVariable Long userId, @PathVariable Long cartItemId){
        cartService.deleteCartItem(userId, cartItemId);
        return "상품이 삭제되었습니다.";
    }
}
