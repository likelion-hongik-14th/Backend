package com.app.mutsa_shoppingmall.Controller;

import com.app.mutsa_shoppingmall.DTO.ProductDto;
import com.app.mutsa_shoppingmall.Service.ProductService;
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
    public ResponseEntity<ProductDto.Response> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }
}
