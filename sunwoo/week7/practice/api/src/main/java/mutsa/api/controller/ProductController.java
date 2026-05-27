package mutsa.api.controller;

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
public class ProductController {
    private final ProductService productService;

    //상품 등록
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(@RequestBody @Valid ProductRequestDto requestDto) {
        ProductResponseDto response = productService.createProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("상품 등록에 성공했습니다.", response));
    }

    //상품 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProduct();
        return ResponseEntity.ok(ApiResponse.onSuccess("상품 전체조회에 성공했습니다.", products));
    }

    //상품 상세페이지 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(@PathVariable Long productId) {
        ProductResponseDto responseDto = productService.getProductById(productId);
        return ResponseEntity.ok(ApiResponse.onSuccess("상품 상세조회에 성공했습니다.", responseDto));
    }
}
