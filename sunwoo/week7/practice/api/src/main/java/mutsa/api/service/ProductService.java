package mutsa.api.service;

import lombok.RequiredArgsConstructor;
import mutsa.api.domain.Product;
import mutsa.api.dto.ProductRequestDto;
import mutsa.api.dto.ProductResponseDto;
import mutsa.api.global.apiPayload.exception.NotFoundException;
import mutsa.api.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    //TODO: 상품 등록
    //상품 등록 기능
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = Product.create(requestDto.getName(), requestDto.getPrice(), requestDto.getStock(), requestDto.getDescription());
        Product savedProduct = productRepository.save(product);
        return ProductResponseDto.from(savedProduct);
    }

    //TODO: 상품 전체 조회
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProduct() {
        return productRepository.findAll().stream().map(ProductResponseDto::from).toList();
    }

    //TODO: 상품 상세 조회
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
        return ProductResponseDto.from(product);
    }
}
