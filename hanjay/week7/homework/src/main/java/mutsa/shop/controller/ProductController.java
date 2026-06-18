package mutsa.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Product", description = "Product API")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "상품 조회", description = "상품 전체 목록을 조회할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON2000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<mutsa.shop.global.apiPayload.ApiResponse<List<ProductResponseDto>>> getAllProducts() {
        List<ProductResponseDto> products = productService.findAllProducts();

        // 💡 세션 PDF 13p 표준 명세서 구조에 맞춰 Map 래핑을 제거하고 List를 result에 직접 바인딩합니다.
        return ResponseEntity.ok(mutsa.shop.global.apiPayload.ApiResponse.onSuccess("상품 전체조회에 성공했습니다.", products));
    }
}
