package mutsa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.ProductRequestDto;
import mutsa.api.dto.ProductResponseDto;
import mutsa.api.global.apiPayload.ApiResponse;
import mutsa.api.global.apiPayload.code.ProductSuccessCode;
import mutsa.api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product API", description = "상품 도메인 관련 API")
public class ProductController {

    private final ProductService productService;

    // [관리자] 신규 상품 등록 API
    @PostMapping
    @Operation(summary = "신규 상품 등록", description = "새로운 상품을 상점에 등록하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT201_1", description = "상품이 성공적으로 등록되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400_1", description = "잘못된 요청(필수값 누락 등)입니다.")
    })
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(@Valid @RequestBody ProductRequestDto requestDto){
        ProductResponseDto responseDto = productService.createProduct(requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(ProductSuccessCode.PRODUCT_CREATED, responseDto));
    }

    // 특정 상품 상세 조회 API
    @GetMapping("/{id}")
    @Operation(summary = "특정 상품 상세 조회", description = "상품 ID를 통해 특정 상품의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT200_1", description = "상품 조회에 성공했습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT404_1", description = "존재하지 않거나 삭제된 상품입니다.")
    })
   public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(@PathVariable Long id){
        ProductResponseDto responseDto = productService.getProduct(id);
        return ResponseEntity.ok(ApiResponse.onSuccess(ProductSuccessCode.PRODUCT_OK, responseDto));
    }

    // 전체 상품 목록 조회 API
    @GetMapping
    @Operation(summary = "전체 상품 목록 조회", description = "삭제되지 않은 전체 상품 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT200_1", description = "상품 목록 조회에 성공했습니다.")
    })
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProducts(){
        List<ProductResponseDto> responseDtoList = productService.getAllProduct();
        return ResponseEntity.ok(ApiResponse.onSuccess(ProductSuccessCode.PRODUCT_OK, responseDtoList));
    }

    // [관리자] 특정 상품 정보 수정 API
    @PatchMapping("/{id}")
    @Operation(summary = "상품 정보 수정", description = "등록된 특정 상품의 정보(가격, 재고 등)를 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT200_2", description = "상품 정보가 성공적으로 수정되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT404_1", description = "존재하지 않거나 삭제된 상품입니다.")
    })
    public ResponseEntity<ApiResponse<Void>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDto requestDto) {
        productService.updateProduct(id, requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(ProductSuccessCode.PRODUCT_UPDATED));
    }

    // [관리자] 특정 상품 삭제 API
    @DeleteMapping("/{id}")
    @Operation(summary = "상품 삭제 (Soft Delete)", description = "특정 상품을 삭제 처리(상태 변경) 합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT200_3", description = "상품이 성공적으로 삭제되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT404_1", description = "존재하지 않거나 이미 삭제된 상품입니다.")
    })
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.onSuccess(ProductSuccessCode.PRODUCT_DELETED));
    }
}