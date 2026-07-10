package mutsa.mutsa_week5_hw.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.dto.CartItemRequestDto;
import mutsa.mutsa_week5_hw.dto.CartItemUpdateDto;
import mutsa.mutsa_week5_hw.dto.CartResponseDto;
import mutsa.mutsa_week5_hw.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts") // 수정: 데이터 요청용 API 및 버전1임을 명시
@RequiredArgsConstructor
@Tag(name = "Cart", description = "장바구니 API")
public class CartController {

    private final CartService cartService;

    // 장바구니 조회
    @Operation(summary = "장바구니 조회", description = "장바구니에 담은 상품 목록을 조회할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "MEMBER_NOT_FOUND / CART_NOT_FOUND")
    })
    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(@RequestParam Long memberId) {
        return ResponseEntity.ok(cartService.getCart(memberId));
    }

    // 장바구니에 상품 추가
    @Operation(summary = "장바구니에 상품 추가", description = "장바구니에 새로운 상품을 담을 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "상품 추가 성공"),
            @ApiResponse(responseCode = "404", description = "MEMBER_NOT_FOUND / PRODUCT_NOT_FOUND"),
            @ApiResponse(responseCode = "409", description = "CART_ITEM_ALREADY_EXISTS"),
            @ApiResponse(responseCode = "400", description = "CART_ITEM_QUANTITY_INVALID")
    })
    @PostMapping("/items")
    public ResponseEntity<CartResponseDto> addItem(
            @RequestParam Long memberId,
            @RequestBody @Valid CartItemRequestDto requestDto) { // 수정: DTO에서 유효성 검사를 하도록 @Valid 추가

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartService.addItem(memberId, requestDto));
    }

    // 장바구니 상품 수량 변경
    @Operation(summary = "장바구니 상품 수량 변경", description = "장바구니에 담은 상품의 수량을 변경할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수량 변경 성공"),
            @ApiResponse(responseCode = "404", description = "CART_ITEM_NOT_FOUND"),
            @ApiResponse(responseCode = "400", description = "CART_ITEM_QUANTITY_INVALID")
    })
    @PatchMapping("/items/{itemId}")
    public ResponseEntity<CartResponseDto> updateItemQuantity(
            @RequestParam Long memberId,
            @PathVariable Long itemId,
            @RequestBody @Valid CartItemUpdateDto requestDto) { // 수정: DTO에서 유효성 검사를 하도록 @Valid 추가

        return ResponseEntity.ok(
                cartService.updateItemQuantity(memberId, itemId, requestDto)
        );
    }

    // 장바구니 상품 삭제
    @Operation(summary = "장바구니에서 상품 삭제", description = "장바구니에 담은 상품을 삭제할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "CART_ITEM_NOT_FOUND")
    })
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @RequestParam Long memberId,
            @PathVariable Long itemId) {
        cartService.deleteItem(memberId, itemId);
        return ResponseEntity.noContent().build();

    }
}
