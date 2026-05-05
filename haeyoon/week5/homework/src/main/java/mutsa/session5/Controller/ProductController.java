package mutsa.session5.Controller;

import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.ProductListResponseDto;
import mutsa.session5.Dto.ProductRequestDto;
import mutsa.session5.Dto.ProductResponseDto;
import mutsa.session5.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 상품 생성
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto requestDto) {
        ProductResponseDto response = productService.createProduct(requestDto);
        return ResponseEntity.ok(response);
    }

    // 상품 조회
    @GetMapping
    public ResponseEntity<ProductListResponseDto> getProducts() {
        ProductListResponseDto response = productService.getProducts();
        return ResponseEntity.ok(response);
    }
}
