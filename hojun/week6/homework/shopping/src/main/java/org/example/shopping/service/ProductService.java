package org.example.shopping.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.domain.Product;
import org.example.shopping.dto.product.ProductRequestDto;
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
    public Long createProduct(ProductRequestDto request){
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
        productRepository.save(product);
        return product.getId();
    }

    public List<ProductRequestDto> getProducts(){
        return productRepository.findAll().stream()
                .map(product -> new ProductRequestDto(product.getName(), product.getPrice(), product.getStock()))
                .toList();
    }

    public void updateProduct(Long productId, ProductRequestDto request){
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("상품이 존재하지 않습니다."));

        product.updateInfo(request);
    }
}
