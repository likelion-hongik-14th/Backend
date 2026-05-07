package mutsa.w5homework.controller;

import lombok.RequiredArgsConstructor;
import mutsa.w5homework.dto.CartResponseDto;
import mutsa.w5homework.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDto> getCart(@PathVariable Long id) {
        CartResponseDto responseDto = cartService.getCart(id);
        return ResponseEntity.ok(responseDto);
    }
}
