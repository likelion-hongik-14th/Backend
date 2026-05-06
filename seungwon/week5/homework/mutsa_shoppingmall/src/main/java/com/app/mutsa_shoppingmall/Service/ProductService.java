package com.app.mutsa_shoppingmall.Service;

import com.app.mutsa_shoppingmall.DTO.ProductDto;
import com.app.mutsa_shoppingmall.Entity.Product;
import com.app.mutsa_shoppingmall.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDto.Response getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto.ProductDetail> details = products.stream()
                .map(p -> new ProductDto.ProductDetail(p.getId(), p.getName(), p.getPrice(), p.getStock()))
                .collect(Collectors.toList());

        return new ProductDto.Response(details);
    }
}
