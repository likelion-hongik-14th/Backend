package mutsa.homework.service;

import lombok.RequiredArgsConstructor;
import mutsa.homework.domain.Product;
import mutsa.homework.domain.User;
import mutsa.homework.dto.product.AddProductRequestDto;
import mutsa.homework.dto.product.ProductListResponseDto;
import mutsa.homework.dto.product.ProductResponseDto;
import mutsa.homework.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDto addProduct(AddProductRequestDto requestDto){

        Optional<Product> optionalProduct = productRepository.findByName(requestDto.name());

        Product product;

        if (optionalProduct.isPresent()) {

            product = optionalProduct.get();
            product.increaseStock(requestDto.stock());
            product.updatePrice(requestDto.price());
        }else {

            Product newProduct = Product.create(requestDto.name(), requestDto.price(), requestDto.stock());
            product = productRepository.save(newProduct);
        }

        return ProductResponseDto.from(product);
    }

    public ProductResponseDto getProduct(Long productId){

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 상품입니다."));

        return ProductResponseDto.from(product);
    }

    public ProductListResponseDto getAllProduct(){

        List<ProductResponseDto> products = productRepository.findAll().stream()
                .map(ProductResponseDto::from)
                .toList();

        return ProductListResponseDto.from(products);
    }
}
