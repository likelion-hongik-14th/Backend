package musta_week5.Controller;

import lombok.RequiredArgsConstructor;
import musta_week5.Dto.ProductRequestDto;
import musta_week5.Dto.ProductUpdateDto;
import musta_week5.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/categories/{id}/products")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductsByCategory(id));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    // 상품 등록
    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDto dto) {
        productService.createProduct(dto);
        return ResponseEntity.ok("상품 등록 완료");
    }

    // 상품 수정
    @PatchMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdateDto dto) {
        productService.updateProduct(id, dto);
        return ResponseEntity.ok("상품 수정 완료");
    }



}
