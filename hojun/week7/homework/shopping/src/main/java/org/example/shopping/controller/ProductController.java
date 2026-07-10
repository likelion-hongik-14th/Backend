package org.example.shopping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.product.ProductRequestDto;
import org.example.shopping.dto.product.ProductResponseDto;
import org.example.shopping.global.apiPayload.ApiResponse;
import org.example.shopping.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product API", description = "물품 도메인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 생성", description = "상품의 상세정보를 받아서 생성한다")
    @PostMapping
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CREATED", description = "상품이 생성되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BAD_REQUEST", description = "가격은 최소 1원 이상이어야 합니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BAD_REQUEST", description = "수량은 최소 1개 이상이어야 합니다.", content = @Content(mediaType = "application/json"))

    })
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(@RequestBody @Validated ProductRequestDto request){
        ProductResponseDto response = productService.createProduct(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess("상품이 생성되었습니다.", response));
    }

    @Operation(summary = "전체 상품 조회")
    @GetMapping
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SUCCESS", description = "전체 상품 조회가 완료되었습니다.", content = @Content(mediaType = "application/json"))})
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProducts(){
        List<ProductResponseDto> products = productService.getProducts();
        return ResponseEntity
                .ok(ApiResponse.onSuccess("전체 상품 조회가 완료되었습니다.", products));
    }

    @Operation(summary = "상품 정보 수정", description = "상품명, 가격 혹은 재고를 수정한다")
    @PatchMapping("/{productId}")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SUCCESS", description = "상품 정보가 수정되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT_NOT_FOUND", description = "존재하지 않는 상품입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BAD_REQUEST", description = "가격은 최소 1원 이상이어야 합니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BAD_REQUEST", description = "수량은 최소 1개 이상이어야 합니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<Void>> updateProduct(@PathVariable Long productId, @RequestBody @Validated ProductRequestDto request){
        productService.updateProduct(productId, request);
        return ResponseEntity
                .ok(ApiResponse.onSuccess("상품 정보가 수정되었습니다."));
    }
}