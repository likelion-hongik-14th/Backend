package mutsa.hw5.controller;

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
public class ProductController {

    private final ProductService productService;

    // 상품 등록
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(@Valid @RequestBody ProductRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("상품이 등록되었습니다.", productService.createProduct(dto)));
    }

    // 상품 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getProducts() {
        return ResponseEntity.ok(ApiResponse.onSuccess("상품 목록 조회 성공", productService.getProducts()));
    }

    // 특정 상품 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(ApiResponse.onSuccess("상품 조회 성공", productService.getProduct(productId)));
    }

    // 상품 수정
    @PatchMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductUpdateDto dto) {
        return ResponseEntity.ok(ApiResponse.onSuccess("상품이 수정되었습니다.", productService.updateProduct(productId, dto)));
    }

    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(ApiResponse.onSuccess("상품이 삭제되었습니다."));
    }
}