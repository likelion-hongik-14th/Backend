package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.domain.Product;
import org.example.shopping.dto.ProductRequestDto;
import org.example.shopping.repository.ProductRepository;
import org.example.shopping.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public String createProduct(@RequestBody ProductRequestDto request){
        productService.createProduct(request);
        return "상품이 등록되었습니다.";
    }
}
