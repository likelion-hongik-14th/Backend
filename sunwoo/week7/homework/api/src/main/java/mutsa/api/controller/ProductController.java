package mutsa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.ProductRequestDto;
import mutsa.api.dto.ProductResponseDto;
import mutsa.api.global.apiPayload.ApiResponse;
import mutsa.api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product API")
public class ProductController {
    private final ProductService productService;

    //상품 등록
    @PostMapping
    @Operation(summary = "상품 등록", description = "상품 정보를 등록하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "상품 등록에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_4001", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(@RequestBody @Valid ProductRequestDto requestDto) {
        ProductResponseDto response = productService.createProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("상품 등록에 성공했습니다.", response));
    }

    //상품 전체 조회
    @GetMapping
    @Operation(summary = "상품 전체 조회", description = "등록된 상품 목록을 조회하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "상품 전체조회에 성공했습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProduct();
        return ResponseEntity.ok(ApiResponse.onSuccess("상품 전체조회에 성공했습니다.", products));
    }

    //상품 상세페이지 조회
    @GetMapping("/{productId}")
    @Operation(summary = "상품 상세 조회", description = "상품 ID로 상품 상세 정보를 조회하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "상품 상세조회에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT_4041", description = "상품을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(@PathVariable Long productId) {
        ProductResponseDto responseDto = productService.getProductById(productId);
        return ResponseEntity.ok(ApiResponse.onSuccess("상품 상세조회에 성공했습니다.", responseDto));
    }
}
