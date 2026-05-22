package mutsa.shop.service;

import lombok.RequiredArgsConstructor;
import mutsa.shop.domain.Product;
import mutsa.shop.dto.ProductResponseDto;
import mutsa.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponseDto> findAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductResponseDto::new)
                .toList();
    }
}
