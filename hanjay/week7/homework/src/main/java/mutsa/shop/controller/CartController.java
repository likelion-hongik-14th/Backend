package mutsa.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import mutsa.shop.dto.cartItemDto.CartItemAddRequestDto;
import mutsa.shop.dto.cartItemDto.CartItemResponseDto;
import mutsa.shop.dto.cartItemDto.CartItemUpdateRequestDto;
import mutsa.shop.dto.CartResponseDto;
import mutsa.shop.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
@Tag(name ="Cart", description = "Cart API")
public class CartController {

    private final CartService cartService;
    private final Long TEMP_MEMBER_ID = 1L;

    @PostMapping("/items")
    @Operation(summary = "장바구니 상품 추가", description = "장바구니에 상품을 추가할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON2000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<mutsa.shop.global.apiPayload.ApiResponse<CartItemResponseDto>> addItem(@RequestBody CartItemAddRequestDto requestDto) {
        CartItemResponseDto responseDto = cartService.addItem(requestDto, TEMP_MEMBER_ID);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mutsa.shop.global.apiPayload.ApiResponse.onSuccess("장바구니에 상품이 성공적으로 추가되었습니다.", responseDto));
    }

    @GetMapping
    @Operation(summary = "장바구니 조회", description = "장바구니에 담긴 상품, 수량등을 조회할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON2000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<mutsa.shop.global.apiPayload.ApiResponse<CartResponseDto>> getCart() {
        CartResponseDto responseDto = cartService.getCart(TEMP_MEMBER_ID);

        return ResponseEntity.ok(mutsa.shop.global.apiPayload.ApiResponse.onSuccess("장바구니 조회에 성공하였습니다.", responseDto));
    }


    @PatchMapping("/items/{itemId}")
    @Operation(summary = "장바구니 상품 수량 변경", description = "장바구니에 담긴 상품의 수량 변경시 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON2000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "CART4041", description = "장바구니 아이템을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<mutsa.shop.global.apiPayload.ApiResponse<CartItemResponseDto>> updateQuantity(
            @PathVariable Long itemId,
            @RequestBody CartItemUpdateRequestDto requestDto) {
        CartItemResponseDto responseDto = cartService.updateItemQuantity(itemId, requestDto);

        return ResponseEntity.ok(mutsa.shop.global.apiPayload.ApiResponse.onSuccess("상품 수량이 성공적으로 변경되었습니다.", responseDto));
    }

    @DeleteMapping("/items/{itemId}")
    @Operation(summary = "장바구니 상품 삭제", description = "장바구니에 담겨 있는 상품 삭제 시 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON2000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "CART4041", description = "장바구니 아이템을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<mutsa.shop.global.apiPayload.ApiResponse<Void>> deleteItem(@PathVariable Long itemId) {
        cartService.deleteItem(itemId);


        return ResponseEntity.ok(mutsa.shop.global.apiPayload.ApiResponse.onSuccess("장바구니에서 상품이 삭제되었습니다."));
    }
}
