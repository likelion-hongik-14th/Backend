package mutsa.api.service;

import lombok.RequiredArgsConstructor;
import mutsa.api.domain.Product;
import mutsa.api.domain.ProductStatus;
import mutsa.api.dto.ProductRequestDto;
import mutsa.api.dto.ProductResponseDto;
import mutsa.api.global.apiPayload.code.ProductErrorCode;
import mutsa.api.global.apiPayload.exception.ProjectException;
import mutsa.api.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    // [생성] 신규 상품 등록
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

        return ProductResponseDto.of(savedProduct);
    }

    // [조회] 특정 상품 상세 정보 조회
    public ProductResponseDto getProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));

        if (product.getStatus() == ProductStatus.DELETED){
            throw new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }

        return ProductResponseDto.of(product);
    }

    // [조회] 전체 상품 목록 조회
    public List<ProductResponseDto> getAllProduct(){
        return productRepository.findAll().stream()
                .filter(product -> product.getStatus() != ProductStatus.DELETED)
                .map(ProductResponseDto::of)
                .toList();
    }

    // [수정] 등록된 특정 상품 정보(재고, 가격 등) 수정
    @Transactional
    public void updateProduct(Long id, ProductRequestDto requestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));

        if (product.getStatus() == ProductStatus.DELETED){
            throw new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }

        product.updateProduct(
                requestDto.getName(),
                requestDto.getPrice(),
                requestDto.getStock(),
                requestDto.getDescription(),
                requestDto.getStatus()
        );
    }

    // [삭제] 등록된 특정 상품 삭제
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND));

        if (product.getStatus() == ProductStatus.DELETED){
            throw new ProjectException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }

        product.markAsDeleted();
    }
}