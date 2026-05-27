package mutsa.mutsa_week5_hw.controller;

import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.dto.ProductRequestDto;
import mutsa.mutsa_week5_hw.dto.ProductResponseDto;
import mutsa.mutsa_week5_hw.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products") // 수정: 데이터 요청용 API 및 버전1임을 명시
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //새로운 상품 생성
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestBody ProductRequestDto requestDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(requestDto));
    }

    //상품 전체 조회
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    //특정 상품 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {

        ProductResponseDto responseDto = productService.getProduct(id);
        return ResponseEntity.ok(responseDto);
    }
}
