package mutsa.mutsa_week5_hw.controller;

import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.dto.ProductRequestDto;
import mutsa.mutsa_week5_hw.dto.ProductResponseDto;
import mutsa.mutsa_week5_hw.global.ApiResponse;
import mutsa.mutsa_week5_hw.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products") // 수정: 데이터 요청용 API 및 버전1임을 명시
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //새로운 상품 생성 -> ApiResponse로 래핑
    @PostMapping
    public ApiResponse<ProductResponseDto> createProduct(
            @RequestBody ProductRequestDto requestDto) {

        ProductResponseDto response = productService.createProduct(requestDto);
        return ApiResponse.onSuccess("상품 생성에 성공했습니다.", response);
    }

    //상품 전체 조회 -> ApiResponse로 래핑
    @GetMapping
    public ApiResponse<List<ProductResponseDto>> getAllProducts()
    {
        List<ProductResponseDto> response = productService.getAllProducts();
        return ApiResponse.onSuccess("전체 상품 조회에 성공했습니다.", response);
    }

    //특정 상품 조회 -> ApiResponse로 래핑
    @GetMapping("/{id}")
    public ApiResponse<ProductResponseDto> getProduct(@PathVariable Long id) {

        ProductResponseDto responseDto = productService.getProduct(id);
        return ApiResponse.onSuccess("특정 상품 조회에 성공했습니다.", responseDto);
    }

}
