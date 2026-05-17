package mutsa.week2.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDto create(ProductCreateRequestDto requestDto) {
        Product product = Product.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .stock(requestDto.getStock())
                .build();

        return ProductResponseDto.from(productRepository.save(product));
    }

    public ProductListResponseDto getProducts() {
        List<ProductResponseDto> products = productRepository.findAll().stream()
                .map(ProductResponseDto::from)
                .toList();
        return new ProductListResponseDto(products);
    }
}
