package mutsa.w5homework.controller;

import lombok.RequiredArgsConstructor;
import mutsa.w5homework.dto.ProductCreateRequestDto;
import mutsa.w5homework.dto.ProductResponseDto;
import mutsa.w5homework.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductCreateRequestDto requestDto) {
        ProductResponseDto responseDto = productService.createProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    //단건조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        ProductResponseDto responseDto = productService.getProduct(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> responseDto = productService.getAllProducts();
        return ResponseEntity.ok(responseDto);
    }
}
