package com.app.mutsa_shoppingmall.controller;

import com.app.mutsa_shoppingmall.dto.ApiResponse;
import com.app.mutsa_shoppingmall.dto.ProductDto;
import com.app.mutsa_shoppingmall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<ProductDto.Response>> getProducts() {
        return ResponseEntity.ok(ApiResponse.onSuccess("상품 조회 성공", productService.getProducts()));
    }
}