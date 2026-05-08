package musta_week5.Controller;

import lombok.RequiredArgsConstructor;
import musta_week5.Dto.CartItemRequestDto;
import musta_week5.Service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartItemController {

    private final CartItemService cartItemService;

    // 장바구니 담기
    @PostMapping("/{cartId}/items")
    public ResponseEntity<?> addItem(@PathVariable Long cartId,
                                     @RequestBody CartItemRequestDto dto) {
        cartItemService.addItem(cartId, dto);
        return ResponseEntity.ok("담기 완료");
    }


    // 장바구니 조회
    @GetMapping("/{cartId}")
    public ResponseEntity<?> getItems(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartItemService.getItems(cartId));
    }

    // 장바구니 삭제
    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long itemId) {
        cartItemService.deleteItem(itemId);
        return ResponseEntity.ok("삭제 완료");
    }


}
