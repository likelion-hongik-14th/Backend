package mutsa.week2.cart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.week2.common.auth.CurrentMemberProvider;
import mutsa.week2.global.apiPayload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cart", description = "장바구니 API")
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CurrentMemberProvider currentMember;

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "장바구니 상품 추가",
            description = "현재 로그인 회원의 장바구니에 상품을 추가합니다. 동일 상품이 이미 담겨 있다면 실패합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2001", description = "장바구니 상품 추가 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_4001", description = "요청 형식이 올바르지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_4001", description = "수량은 1 이상이어야 합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT_4041", description = "해당 상품은 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_4092", description = "이미 장바구니에 담긴 상품입니다.")
    })
    public ApiResponse<CartItemResponseDto> addItem(
            @Valid @RequestBody CartAddItemRequestDto requestDto) {
        return ApiResponse.onSuccess("장바구니 상품 추가 성공",
                cartService.addItem(currentMember.currentMemberId(), requestDto));
    }

    @GetMapping
    @Operation(summary = "장바구니 조회", description = "현재 로그인 회원의 장바구니 전체 항목을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2000", description = "장바구니 조회 성공")
    })
    public ApiResponse<CartResponseDto> getCart() {
        return ApiResponse.onSuccess("장바구니 조회 성공",
                cartService.getCart(currentMember.currentMemberId()));
    }

    @PatchMapping("/items/{itemId}")
    @Operation(summary = "장바구니 상품 수량 변경",
            description = "장바구니에 담긴 상품의 수량을 변경합니다. 수량은 1 이상이어야 합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2000", description = "장바구니 상품 수량 변경 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_4001", description = "수량은 1 이상이어야 합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_4031", description = "본인의 장바구니 상품만 접근할 수 있습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_4041", description = "해당 장바구니 상품은 존재하지 않습니다.")
    })
    public ApiResponse<CartItemResponseDto> updateQuantity(
            @PathVariable Long itemId,
            @Valid @RequestBody CartUpdateQuantityRequestDto requestDto) {
        return ApiResponse.onSuccess("장바구니 상품 수량 변경 성공",
                cartService.updateQuantity(currentMember.currentMemberId(), itemId, requestDto));
    }

    @DeleteMapping("/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "장바구니 상품 삭제", description = "장바구니에 담긴 상품을 삭제합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2000", description = "장바구니 상품 삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_4031", description = "본인의 장바구니 상품만 접근할 수 있습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_4041", description = "해당 장바구니 상품은 존재하지 않습니다.")
    })
    public ApiResponse<Void> deleteItem(@PathVariable Long itemId) {
        cartService.deleteItem(currentMember.currentMemberId(), itemId);
        return ApiResponse.onSuccess("장바구니 상품 삭제 성공");
    }

    @PatchMapping("/items/{itemId}/image")
    @Operation(summary = "장바구니 상품 이미지 변경",
            description = "장바구니에 담긴 상품의 대표 이미지 URL을 변경합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2000", description = "장바구니 상품 이미지 변경 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_4031", description = "본인의 장바구니 상품만 접근할 수 있습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_4041", description = "해당 장바구니 상품은 존재하지 않습니다.")
    })
    public ApiResponse<CartItemResponseDto> updateImage(
            @PathVariable Long itemId,
            @Valid @RequestBody CartUpdateImageRequestDto requestDto) {
        return ApiResponse.onSuccess("장바구니 상품 이미지 변경 성공",
                cartService.updateImage(currentMember.currentMemberId(), itemId, requestDto));
    }
}
