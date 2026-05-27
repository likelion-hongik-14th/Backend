package org.example.shopping.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.domain.Product;
import org.example.shopping.dto.product.ProductRequestDto;
import org.example.shopping.dto.product.ProductResponseDto;
import org.example.shopping.global.apiPayload.code.domain.ProductErrorCode;
import org.example.shopping.global.apiPayload.exception.ProjectException;
import org.example.shopping.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto request){
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
        productRepository.save(product);
        return ProductResponseDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    public List<ProductRequestDto> getProducts(){
        return productRepository.findAll().stream()
                .map(product -> new ProductRequestDto(product.getName(), product.getDescription(), product.getPrice(), product.getStock()))
                .toList();
    }

    @Transactional
    public void updateProduct(Long productId, ProductRequestDto request){
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));

        product.updateInfo(request);
    }
}
