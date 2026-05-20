package mutsa.hw5.service;

import lombok.RequiredArgsConstructor;
import mutsa.hw5.domain.Product;
import mutsa.hw5.dto.product.ProductRequestDto;
import mutsa.hw5.dto.product.ProductResponseDto;
import mutsa.hw5.dto.product.ProductUpdateDto;
import mutsa.hw5.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service // JPA에게 이 클래스가 서비스 클래스라는 걸 명시

// final 필드들의 생성자를 자동 생성
// Spring이 이 생성자를 보고 Repository들을 자동으로 주입해줌 (의존성 주입)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 등록
    @Transactional // SQLD에서 나오는 ACID 중에 그 원자성을 의미
    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Product product = dto.toEntity();
        productRepository.save(product);
        return ProductResponseDto.from(product);
    }

    // 상품 전체 조회
    @Transactional(readOnly = true) // "readOnly = true"의 의미: DB를 조회만 하고 변경은 안 한다는 뜻
    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    // 특정 상품 조회
    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        return ProductResponseDto.from(product);
    }

    // 상품 수정
    @Transactional
    public ProductResponseDto updateProduct(Long productId, ProductUpdateDto dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        product.update(dto);
        return ProductResponseDto.from(product);
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        productRepository.delete(product);
    }
}