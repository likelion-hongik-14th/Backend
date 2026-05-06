package mutsa.homework.controller;

import lombok.RequiredArgsConstructor;
import mutsa.homework.dto.product.AddProductRequestDto;
import mutsa.homework.dto.product.ProductListResponseDto;
import mutsa.homework.dto.product.ProductResponseDto;
import mutsa.homework.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestBody AddProductRequestDto requestDto
    ) {
        ProductResponseDto responseDto = productService.addProduct(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProduct(
            @PathVariable("productId") Long productId
    ) {
        ProductResponseDto responseDto = productService.getProduct(productId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<ProductListResponseDto> getAllProduct(){
        ProductListResponseDto responseDto = productService.getAllProduct();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
