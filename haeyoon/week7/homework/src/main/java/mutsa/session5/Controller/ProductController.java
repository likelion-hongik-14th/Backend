package mutsa.session5.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.ProductListResponseDto;
import mutsa.session5.Dto.ProductRequestDto;
import mutsa.session5.Dto.ProductResponseDto;
import mutsa.session5.Service.ProductService;
import mutsa.session5.global.apipayload.ApiResponse;
import mutsa.session5.global.apipayload.exception.code.ProductSuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "상품(Product) API", description = "상품 등록 및 조회 API")
public class ProductController {
    private final ProductService productService;

    // 상품 생성
    @PostMapping
    @Operation(summary = "상품 등록", description = "새로운 상품 정보를 받아 시스템에 등록")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "상품이 등록되었습니다. (PRODUCT_201)",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "상품 재고는 0개 미만이 될 수 없습니다. (PRODUCT_400_1)",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(@Valid @RequestBody ProductRequestDto requestDto) {
        ProductResponseDto response = productService.createProduct(requestDto);
        ProductSuccessCode successCode = ProductSuccessCode.CREATE_PRODUCT_SUCCESS;

        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(ApiResponse.onSuccess(successCode, response));
    }

    // 상품 조회
    @GetMapping("/{productId}")
    @Operation(summary = "상품 상세 조회", description = "상품 ID를 경로 변수로 받아 해당 상품의 상세 정보를 조회")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "상품 상세 정보 조회가 완료되었습니다. (PRODUCT_200_1)",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "해당 상품을 찾을 수 없습니다. (PRODUCT_404_1)",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(@PathVariable Long productId) {
        ProductResponseDto response = productService.getProduct(productId);
        ProductSuccessCode successCode = ProductSuccessCode.GET_PRODUCT_SUCCESS;

        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(ApiResponse.onSuccess(successCode, response));
    }
    @GetMapping
    @Operation(summary = "상품 전체 목록 조회", description = "현재 등록되어 있는 모든 상품의 리스트를 한눈에 조회")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "상품 전체 목록 조회가 완료되었습니다. (PRODUCT_200_2)",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<ApiResponse<ProductListResponseDto>> getProducts() {
        ProductListResponseDto response = productService.getProducts(); // 서비스의 전체 조회 호출
        ProductSuccessCode successCode = ProductSuccessCode.GET_PRODUCT_LIST_SUCCESS;

        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(ApiResponse.onSuccess(successCode, response));
    }
}
