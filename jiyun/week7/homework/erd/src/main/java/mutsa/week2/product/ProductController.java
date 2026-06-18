package mutsa.week2.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.week2.global.apiPayload.ApiResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Product", description = "상품 API")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "상품 등록", description = "관리자가 신규 상품을 등록합니다. 가격과 재고는 0 이상이어야 합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2001", description = "상품 등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_4001", description = "요청 형식이 올바르지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT_4001", description = "재고는 0 이상이어야 합니다.")
    })
    public ApiResponse<ProductResponseDto> createProduct(
            @Valid @RequestBody ProductCreateRequestDto requestDto) {
        return ApiResponse.onSuccess("상품 등록 성공", productService.create(requestDto));
    }

    @GetMapping
    @Operation(summary = "상품 목록 조회", description = "페이징 파라미터를 받아 상품 목록을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2000", description = "상품 목록 조회 성공")
    })
    public ApiResponse<ProductListResponseDto> getProducts(
            @ParameterObject
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.onSuccess("상품 목록 조회 성공", productService.getProducts(pageable));
    }
}
