package mutsa.homework.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.homework.controller.docs.ProductApiDocs;
import mutsa.homework.dto.product.AddProductRequestDto;
import mutsa.homework.dto.product.ProductResponseDto;
import mutsa.homework.global.apiPayload.GlobalResponse;
import mutsa.homework.global.dto.ListResponseDto;
import mutsa.homework.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController implements ProductApiDocs {

    private final ProductService productService;

    @Override
    @PostMapping
    public ResponseEntity<GlobalResponse<ProductResponseDto>> createProduct(
            @Valid
            @RequestBody AddProductRequestDto requestDto
    ) {
        ProductResponseDto responseDto = productService.addProduct(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.onSuccessCreate(responseDto));
    }

    @Override
    @GetMapping("/{productId}")
    public ResponseEntity<GlobalResponse<ProductResponseDto>> getProduct(
            @PathVariable("productId") Long productId
    ) {
        ProductResponseDto responseDto = productService.getProduct(productId);

        return ResponseEntity.ok(GlobalResponse.onSuccess(responseDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<GlobalResponse<ListResponseDto<ProductResponseDto>>> getAllProduct(){
        ListResponseDto<ProductResponseDto> responseDto = productService.getAllProduct();

        return ResponseEntity.ok(GlobalResponse.onSuccess(responseDto));
    }

}
