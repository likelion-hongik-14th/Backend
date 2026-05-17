package mutsa.week2.cart;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<CartItemResponseDto> addItem(@Valid @RequestBody CartAddItemRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addItem(requestDto));
    }

    @GetMapping
    public ResponseEntity<CartResponseDto> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    @PatchMapping("/items/{itemId}")
    public ResponseEntity<CartItemResponseDto> updateQuantity(
            @PathVariable Long itemId,
            @Valid @RequestBody CartUpdateQuantityRequestDto requestDto
    ) {
        return ResponseEntity.ok(cartService.updateQuantity(itemId, requestDto));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        cartService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/items/{itemId}/image")
    public ResponseEntity<CartItemResponseDto> updateImage(
            @PathVariable Long itemId,
            @Valid @RequestBody CartUpdateImageRequestDto requestDto
    ) {
        return ResponseEntity.ok(cartService.updateImage(itemId, requestDto));
    }
}
