package mutsa.w5homework.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.w5homework.dto.CartItemCreateRequestDto;
import mutsa.w5homework.dto.CartItemResponseDto;
import mutsa.w5homework.service.CartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/carts/items")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<CartItemResponseDto> createCartItem(@Valid @RequestBody CartItemCreateRequestDto requestDto) {
        CartItemResponseDto responseDto = cartItemService.createCartItem(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
