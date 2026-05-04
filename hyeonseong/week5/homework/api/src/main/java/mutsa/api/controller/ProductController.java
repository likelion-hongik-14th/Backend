package mutsa.api.controller;

import lombok.RequiredArgsConstructor;
import mutsa.api.dto.ProductRequestDto;
import mutsa.api.dto.ProductResponseDto;
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

    // 신규 상품 등록 API
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto requestDto){

        ProductResponseDto responseDto = productService.createProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 특정 상품 상세 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id){

        ProductResponseDto responseDto = productService.getProduct(id);
        return ResponseEntity.ok(responseDto);
    }

    // 전체 상품 목록 조회 API
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        List<ProductResponseDto> responseDtoList = productService.getAllProduct();
        return ResponseEntity.ok(responseDtoList);
    }
}
