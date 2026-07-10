package mutsa.mutsa_week5_hw.service;

import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.domain.Product;
import mutsa.mutsa_week5_hw.dto.ProductRequestDto;
import mutsa.mutsa_week5_hw.dto.ProductResponseDto;
import mutsa.mutsa_week5_hw.global.code.GeneralCode;
import mutsa.mutsa_week5_hw.global.exception.GeneralException;
import mutsa.mutsa_week5_hw.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 수정: 메서드 안의 DB 작업을 트랜잭션으로 묶어 처리하도록 어노테이션 추가
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    //새로운 상품 생성
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {

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
                .orElseThrow(() -> new GeneralException(GeneralCode.PRODUCT_NOT_FOUND));

        return ProductResponseDto.from(product);
    }

    //상품 삭제
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new GeneralException(GeneralCode.PRODUCT_NOT_FOUND));

        productRepository.delete(product);
    }
}
