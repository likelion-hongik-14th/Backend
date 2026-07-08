package com.study.shop.controller;

import com.study.shop.dto.product.ProductListResponse;
import com.study.shop.dto.product.ProductRequest;
import com.study.shop.dto.product.ProductResponse;
import com.study.shop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.study.shop.global.apiPayload.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "상품 API")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "상품명, 가격, 재고, 설명, 판매 상태를 입력받아 상품을 등록합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "상품 등록 성공",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청입니다.",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess("상품 등록 성공", response));
    }

    @Operation(summary = "상품 목록 조회", description = "삭제되지 않은 상품 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "상품 목록 조회 성공",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductListResponse>>> getProducts() {
        List<ProductListResponse> response = productService.getProducts();

        return ResponseEntity
                .ok(ApiResponse.onSuccess("상품 목록 조회 성공", response));
    }

    @Operation(summary = "상품 상세 조회", description = "productId를 기준으로 상품 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "상품 상세 조회 성공",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "PRODUCT404_1 - 상품을 찾을 수 없습니다.",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "PRODUCT400_1 - 삭제된 상품입니다.",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long productId) {
        ProductResponse response = productService.getProduct(productId);

        return ResponseEntity
                .ok(ApiResponse.onSuccess("상품 상세 조회 성공", response));
    }

    @Operation(summary = "상품 수정", description = "productId를 기준으로 상품 정보를 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "상품 수정 성공",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "PRODUCT404_1 - 상품을 찾을 수 없습니다.",
                    content = @Content(mediaType = "application/json"))
    })
    @PatchMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.updateProduct(productId, request);

        return ResponseEntity
                .ok(ApiResponse.onSuccess("상품 수정 성공", response));
    }

    @Operation(summary = "상품 삭제", description = "productId를 기준으로 상품 상태를 삭제 상태로 변경합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "상품 삭제 성공",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "PRODUCT404_1 - 상품을 찾을 수 없습니다.",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity
                .ok(ApiResponse.onSuccess("상품 삭제 성공"));
    }
}
