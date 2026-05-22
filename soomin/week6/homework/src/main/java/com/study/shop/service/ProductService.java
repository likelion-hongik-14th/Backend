package com.study.shop.service;

import com.study.shop.domain.Product;
import com.study.shop.domain.ProductStatus;
import com.study.shop.dto.product.ProductListResponse;
import com.study.shop.dto.product.ProductRequest;
import com.study.shop.dto.product.ProductResponse;
import com.study.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {

        Product product = new Product(
                request.getName(),
                request.getPrice(),
                request.getStockQuantity(),
                request.getDescription(),
                request.getStatus()
        );

        Product savedProduct = productRepository.save(product);

        return new ProductResponse(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductListResponse> getProducts() {
        return productRepository.findAllByStatusNot(ProductStatus.DELETE)
                .stream()
                .map(ProductListResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + productId));

        if (product.getStatus() == ProductStatus.DELETE) {
            throw new IllegalArgumentException("삭제된 상품입니다. id=" + productId);
        }

        return new ProductResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + productId));

        product.update(
                request.getName(),
                request.getPrice(),
                request.getStockQuantity(),
                request.getDescription(),
                request.getStatus()
        );

        return new ProductResponse(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + productId));

        product.delete();
    }
}
