package mutsa.api.service;

import lombok.RequiredArgsConstructor;
import mutsa.api.domain.Product;
import mutsa.api.dto.ProductRequestDto;
import mutsa.api.dto.ProductResponseDto;
import mutsa.api.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository; // 의존성 주입!

    // 상품 등록 기능
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto){

        Product product = Product.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .stock(requestDto.getStock())
                .description(requestDto.getDescription())
                .status(requestDto.getStatus())
                .build();

        Product savedProduct = productRepository.save(product);

        return new ProductResponseDto(savedProduct);
    }

    // 상품 조회 기능
    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long id){

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. id=" + id));

        return new ProductResponseDto(product);
    }

    // 전체 상품 목록 조회 기능
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProduct(){
        return productRepository.findAll().stream()
                .map(ProductResponseDto::new)
                .toList();
    }
}
