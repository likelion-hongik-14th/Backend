package mutsa.session5.Service;

import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.ProductListResponseDto;
import mutsa.session5.Dto.ProductRequestDto;
import mutsa.session5.Dto.ProductResponseDto;
import mutsa.session5.Entity.Product;
import mutsa.session5.Repository.ProductRepository;
import mutsa.session5.global.apipayload.exception.ProductException;
import mutsa.session5.global.apipayload.exception.code.ProductErrorCode;
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

        if (requestDto.getStock() < 0) {
            throw new ProductException(ProductErrorCode.INVALID_STOCK);
        }

        Product product = Product.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .stock(requestDto.getStock())
                .build();

        Product savedProduct = productRepository.save(product);

        return ProductResponseDto.from(savedProduct);
    }

    // 상품 조회
    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        return ProductResponseDto.from(product);
    }

    @Transactional(readOnly = true)
    public ProductListResponseDto getProducts() {
        List<Product> products = productRepository.findAll();

        return ProductListResponseDto.from(products);
    }
}
