package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.product.ProductRequestDto;
import org.example.shopping.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody ProductRequestDto request){
        Long productId = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

    @GetMapping
    public ResponseEntity<List<ProductRequestDto>> getAllProducts(){
        List<ProductRequestDto> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDto request){
        productService.updateProduct(productId, request);
        return ResponseEntity.ok().build();
    }
}
