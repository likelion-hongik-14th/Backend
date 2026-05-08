package musta_week5.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import musta_week5.Dto.ProductRequestDto;
import musta_week5.Dto.ProductResponseDto;
import musta_week5.Dto.ProductUpdateDto;
import musta_week5.Repository.CategoryRepository;
import musta_week5.Repository.ProductRepository;
import musta_week5.domain.Category;
import musta_week5.domain.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// ProductService.java
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(p -> new ProductResponseDto(
                        p.getId(), p.getProductName(), p.getPrice(),
                        p.getCategory().getId()))
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategory_Id(categoryId)
                .stream()
                .map(p -> new ProductResponseDto(
                        p.getId(), p.getProductName(), p.getPrice(),
                        p.getCategory().getId()))
                .collect(Collectors.toList());
    }

    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품 없음"));
        return new ProductResponseDto(
                product.getId(), product.getProductName(), product.getPrice(),
                product.getCategory().getId());
    }

    public void createProduct(ProductRequestDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다."));

        Product product = Product.builder()
                .productName(dto.getName())
                .price(dto.getPrice())
                .category(category)
                .createdAt(LocalDateTime.now())
                .build();

        productRepository.save(product);
    }

    public void updateProduct(Long id, ProductUpdateDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        if (dto.getProductName() != null) product.updateProductName(dto.getProductName());
        if (dto.getPrice() != null) product.updatePrice(dto.getPrice());
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다."));
            product.updateCategory(category);
        }
    }

}
