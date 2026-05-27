package mutsa.homework.service;

import lombok.RequiredArgsConstructor;
import mutsa.homework.domain.Product;
import mutsa.homework.domain.User;
import mutsa.homework.dto.product.AddProductRequestDto;
import mutsa.homework.dto.product.ProductListResponseDto;
import mutsa.homework.dto.product.ProductResponseDto;
import mutsa.homework.global.dto.ListResponseDto;
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

        if(productRepository.existsByName(requestDto.name())){
            throw new IllegalArgumentException("해당 이름의 상품이 이미 존재합니다. '" + requestDto.name() + "'");
        }

        Product newProduct = Product.create(requestDto.name(), requestDto.price(), requestDto.stock());
        productRepository.save(newProduct);

        return ProductResponseDto.from(newProduct);
    }

    public ProductResponseDto getProduct(Long productId){

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 상품입니다."));

        return ProductResponseDto.from(product);
    }

    public ListResponseDto<ProductResponseDto> getAllProduct(){

        List<ProductResponseDto> products = productRepository.findAll().stream()
                .map(ProductResponseDto::from)
                .toList();

        return ListResponseDto.of(products);
    }
}
