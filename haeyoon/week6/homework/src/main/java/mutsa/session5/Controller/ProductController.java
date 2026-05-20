package mutsa.session5.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.ProductListResponseDto;
import mutsa.session5.Dto.ProductRequestDto;
import mutsa.session5.Dto.ProductResponseDto;
import mutsa.session5.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 상품 생성
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto) {
        ProductResponseDto response = productService.createProduct(requestDto);
        return ResponseEntity.ok(response);
    }

    // 상품 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long productId) {
        ProductResponseDto response = productService.getProduct(productId);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<ProductListResponseDto> getProducts() {
        ProductListResponseDto response = productService.getProducts(); // 서비스의 전체 조회 호출
        return ResponseEntity.ok(response);
    }
}
