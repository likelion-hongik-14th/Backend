package mutsa.session5.Controller;

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
public class ProductController {
    private final ProductService productService;

    // 상품 생성
    @PostMapping
    public ApiResponse<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto) {
        ProductResponseDto response = productService.createProduct(requestDto);
        return ApiResponse.onSuccess(ProductSuccessCode.CREATE_PRODUCT_SUCCESS.getMessage(), response);
    }

    // 상품 조회
    @GetMapping("/{productId}")
    public ApiResponse<ProductResponseDto> getProduct(@PathVariable Long productId) {
        ProductResponseDto response = productService.getProduct(productId);
        return ApiResponse.onSuccess(ProductSuccessCode.GET_PRODUCT_SUCCESS.getMessage(), response);
    }
    @GetMapping
    public ApiResponse<ProductListResponseDto> getProducts() {
        ProductListResponseDto response = productService.getProducts(); // 서비스의 전체 조회 호출
        return ApiResponse.onSuccess(ProductSuccessCode.GET_PRODUCT_LIST_SUCCESS.getMessage(), response);
    }
}
