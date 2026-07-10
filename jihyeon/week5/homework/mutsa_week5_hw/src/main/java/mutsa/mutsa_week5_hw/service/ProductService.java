package mutsa.mutsa_week5_hw.service;

import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.domain.Product;
import mutsa.mutsa_week5_hw.dto.ProductRequestDto;
import mutsa.mutsa_week5_hw.dto.ProductResponseDto;
import mutsa.mutsa_week5_hw.global.code.ProductErrorCode;
import mutsa.mutsa_week5_hw.global.exception.ProjectException;
import mutsa.mutsa_week5_hw.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional // 수정: 메서드 안의 DB 작업을 트랜잭션으로 묶어 처리하도록 어노테이션 추가
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    //새로운 상품 생성
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {

        // 상품 가격 검증
        if (requestDto.getPrice() < 0) {
            throw new ProjectException(
                    ProductErrorCode.INVALID_PRODUCT_PRICE
            );
        }

        // 중복 상품 검증
        if (productRepository.existsByName(requestDto.getName())) {
            throw new ProjectException(
                    ProductErrorCode.PRODUCT_ALREADY_EXISTS
            );
        }

        // 재고 검증
        if (requestDto.getStock() < 0) {
            throw new ProjectException(
                    ProductErrorCode.OUT_OF_STOCK
            );
        }

        Product product = Product.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .stock(requestDto.getStock())
                .build();

        Product saved = productRepository.save(product);

        return ProductResponseDto.from(saved);
    }

    //전체 상품 조회
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDto::from)
                .toList();
    }

    //특정 상품 조회
    public ProductResponseDto getProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProjectException(
                        ProductErrorCode.PRODUCT_NOT_FOUND));

        return ProductResponseDto.from(product);
    }
}
