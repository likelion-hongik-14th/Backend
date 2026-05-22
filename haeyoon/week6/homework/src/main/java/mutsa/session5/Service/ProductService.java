package mutsa.session5.Service;

import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.ProductListResponseDto;
import mutsa.session5.Dto.ProductRequestDto;
import mutsa.session5.Dto.ProductResponseDto;
import mutsa.session5.Entity.Product;
import mutsa.session5.Repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // 상품 생성
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = Product.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .stock(requestDto.getStock())
                .build();

        Product savedProduct = productRepository.save(product);

        return ProductResponseDto.builder()
                .productId(savedProduct.getProductId())
                .name(savedProduct.getName())
                .price(savedProduct.getPrice())
                .stock(savedProduct.getStock())
                .build();
    }

    // 상품 조회
    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + productId));

        return ProductResponseDto.builder() // 리스트가 아닌 단일 DTO 반환
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    @Transactional(readOnly = true)
    public ProductListResponseDto getProducts() {
        List<ProductResponseDto> list = productRepository.findAll().stream()
                .map(p -> ProductResponseDto.builder()
                        .productId(p.getProductId())
                        .name(p.getName())
                        .price(p.getPrice())
                        .stock(p.getStock())
                        .build())
                .toList();

        return ProductListResponseDto.builder()
                .products(list)
                .build();
    }
}
