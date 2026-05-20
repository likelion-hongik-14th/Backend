package com.app.mutsa_shoppingmall.Service;

import com.app.mutsa_shoppingmall.DTO.ProductDto;
import com.app.mutsa_shoppingmall.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDto.Response getProducts() {
        return new ProductDto.Response(
                productRepository.findAll().stream()
                        .map(ProductDto.ProductDetail::from)
                        .toList()
        );
    }
}