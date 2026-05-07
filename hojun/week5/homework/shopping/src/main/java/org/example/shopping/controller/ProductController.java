package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.domain.Product;
import org.example.shopping.dto.ProductRequestDto;
import org.example.shopping.repository.ProductRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ProductController {

    private final ProductRepository productRepository;

    @PostMapping("/products")
    public String createProduct(@RequestBody ProductRequestDto request){
        productRepository.save(Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .build());
        return "상품 등록 완료";
    }
}
