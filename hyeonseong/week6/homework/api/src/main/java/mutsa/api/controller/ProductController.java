package mutsa.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.ProductRequestDto;
import mutsa.api.dto.ProductResponseDto;
import mutsa.api.global.apiPayload.ApiResponse;
import mutsa.api.global.apiPayload.code.ProductSuccessCode;
import mutsa.api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // [관리자] 신규 상품 등록 API
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(@Valid @RequestBody ProductRequestDto requestDto){
        ProductResponseDto responseDto = productService.createProduct(requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(ProductSuccessCode.PRODUCT_CREATED, responseDto));
    }

    // 특정 상품 상세 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(@PathVariable Long id){
        ProductResponseDto responseDto = productService.getProduct(id);
        return ResponseEntity.ok(ApiResponse.onSuccess(ProductSuccessCode.PRODUCT_OK, responseDto));
    }

    // 전체 상품 목록 조회 API
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProducts(){
        List<ProductResponseDto> responseDtoList = productService.getAllProduct();
        return ResponseEntity.ok(ApiResponse.onSuccess(ProductSuccessCode.PRODUCT_OK, responseDtoList));
    }

    // [관리자] 특정 상품 정보 수정 API
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDto requestDto) {

        productService.updateProduct(id, requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(ProductSuccessCode.PRODUCT_UPDATED));
    }

    // [관리자] 특정 상품 삭제 API
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.onSuccess(ProductSuccessCode.PRODUCT_DELETED));
    }
}