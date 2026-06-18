package mutsa.homework.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mutsa.homework.dto.product.AddProductRequestDto;
import mutsa.homework.dto.product.ProductResponseDto;
import mutsa.homework.global.apiPayload.GlobalResponse;
import mutsa.homework.global.dto.ListResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Product API", description = "상품 도메인 API")
public interface ProductApiDocs {

    @Operation(summary = "상품 추가", description = "판매할 상품을 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "상품 추가 성공(COMMON_201_1)"),
            @ApiResponse(
                    responseCode = "409",
                    description = "상품 중복(PRODUCT_409_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            )
    })
    ResponseEntity<GlobalResponse<ProductResponseDto>> createProduct(
            AddProductRequestDto requestDto
    );

    @Operation(summary = "단일 상품 조회", description = "단일 상품의 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "단일 상품 조회 성공(COMMON_200_1)"),
            @ApiResponse(
                    responseCode = "404",
                    description = "상품 조회 실패(PRODUCT_404_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            )
    })
    ResponseEntity<GlobalResponse<ProductResponseDto>> getProduct(
            Long productId
    );

    @Operation(summary = "전체 상품 조회", description = "전체 상품 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "전체 상품 조회 성공(COMMON_200_1)")
    })
    ResponseEntity<GlobalResponse<ListResponseDto<ProductResponseDto>>> getAllProduct();
}
