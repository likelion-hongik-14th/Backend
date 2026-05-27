package mutsa.homework.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.homework.dto.product.AddProductRequestDto;
import mutsa.homework.dto.product.ProductListResponseDto;
import mutsa.homework.dto.product.ProductResponseDto;
import mutsa.homework.global.dto.ApiResponse;
import mutsa.homework.global.dto.ListResponseDto;
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
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(
            @Valid
            @RequestBody AddProductRequestDto requestDto
    ) {
        ProductResponseDto responseDto = productService.addProduct(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(responseDto));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(
            @PathVariable("productId") Long productId
    ) {
        ProductResponseDto responseDto = productService.getProduct(productId);

        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ListResponseDto<ProductResponseDto>>> getAllProduct(){
        ListResponseDto<ProductResponseDto> responseDto = productService.getAllProduct();

        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

}
