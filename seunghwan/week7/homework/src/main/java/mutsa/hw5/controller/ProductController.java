package mutsa.hw5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mutsa.hw5.dto.product.ProductRequestDto;
import mutsa.hw5.dto.product.ProductResponseDto;
import mutsa.hw5.dto.product.ProductUpdateDto;
import mutsa.hw5.global.apiPayload.ApiResponse;
import mutsa.hw5.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "Product", description = "상품 관련 API")
public class ProductController {

    private final ProductService productService;

    // 상품 등록
    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "상품 등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 값입니다.")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(@Valid @RequestBody ProductRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("상품이 등록되었습니다.", productService.createProduct(dto)));
    }

    // 상품 전체 조회
    @Operation(summary = "상품 전체 조회", description = "등록된 모든 상품을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "상품 목록 조회 성공")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getProducts() {
        return ResponseEntity.ok(ApiResponse.onSuccess("상품 목록 조회 성공", productService.getProducts()));
    }

    // 특정 상품 조회
    @Operation(summary = "상품 단일 조회", description = "특정 상품을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "상품 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "PRODUCT404_1: 상품을 찾을 수 없습니다.")
    })
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(ApiResponse.onSuccess("상품 조회 성공", productService.getProduct(productId)));
    }

    // 상품 수정
    @Operation(summary = "상품 수정", description = "특정 상품의 정보를 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "상품 수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "PRODUCT404_1: 상품을 찾을 수 없습니다.")
    })
    @PatchMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductUpdateDto dto) {
        return ResponseEntity.ok(ApiResponse.onSuccess("상품이 수정되었습니다.", productService.updateProduct(productId, dto)));
    }

    // 상품 삭제
    @Operation(summary = "상품 삭제", description = "특정 상품을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "상품 삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "PRODUCT404_1: 상품을 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "PRODUCT400_1: 주문 이력이 있는 상품은 삭제할 수 없습니다.")
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(ApiResponse.onSuccess("상품이 삭제되었습니다."));
    }
}