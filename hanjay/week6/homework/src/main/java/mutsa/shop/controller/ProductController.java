package mutsa.shop.controller;

import lombok.RequiredArgsConstructor;
import mutsa.shop.dto.ProductResponseDto;
import mutsa.shop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Map<String, List<ProductResponseDto>>> getAllProducts() {
        List<ProductResponseDto> products = productService.findAllProducts();

        return ResponseEntity.ok(Map.of("products", products));
    }
}
