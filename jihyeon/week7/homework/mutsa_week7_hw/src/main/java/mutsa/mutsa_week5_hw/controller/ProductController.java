package mutsa.mutsa_week5_hw.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.dto.ProductRequestDto;
import mutsa.mutsa_week5_hw.dto.ProductResponseDto;
import mutsa.mutsa_week5_hw.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products") // 수정: 데이터 요청용 API 및 버전1임을 명시
@RequiredArgsConstructor
@Tag(name = "Product", description = "상품 API")
public class ProductController {

    private final ProductService productService;

    //새로운 상품 생성
    @Operation(summary = "새 상품 등록", description = "새로운 상품을 등록할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "상품 생성 성공"),
            @ApiResponse(responseCode = "400", description = "PRODUCT_STOCK_ZERO / PRODUCT_OUT_OF_STOCK"),
            @ApiResponse(responseCode = "409", description = "PRODUCT_ALREADY_EXISTS")
    })
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestBody @Valid ProductRequestDto requestDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(requestDto));
    }

    //상품 전체 조회
    @Operation(summary = "전체 상품 조회", description = "등록된 전체 상품을 조회할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    //특정 상품 조회
    @Operation(summary = "특정 상품 조회", description = "등록된 단일 상품을 조회할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 조회 성공"),
            @ApiResponse(responseCode = "404", description = "PRODUCT_NOT_FOUND")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {

        ProductResponseDto responseDto = productService.getProduct(id);
        return ResponseEntity.ok(responseDto);
    }

    //상품 삭제
    @Operation(summary = "상품 삭제", description = "등록된 단일 상품을 삭제할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "상품 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "PRODUCT_NOT_FOUND")
    })
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
