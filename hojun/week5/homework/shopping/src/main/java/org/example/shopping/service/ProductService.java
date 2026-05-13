package org.example.shopping.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.domain.Product;
import org.example.shopping.dto.ProductRequestDto;
import org.example.shopping.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequestDto request){
        productRepository.save(Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .build());
    }
}
