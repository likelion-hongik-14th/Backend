package mutsa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.AddressRequestDto;
import mutsa.api.dto.AddressResponseDto;
import mutsa.api.global.apiPayload.code.AddressSuccessCode;
import mutsa.api.service.AddressService;
import mutsa.api.global.apiPayload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
@Tag(name = "Address API", description = "배송지 관련 API")
public class AddressController {

    private final AddressService addressService;

    // 새로운 배송지 등록 API
    @PostMapping
    @Operation(summary = "배송지 등록", description = "새로운 배송지 정보를 등록합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS201_1", description = "배송지가 성공적으로 등록되었습니다.")
    })
    public ResponseEntity<ApiResponse<Void>> createAddress(
            @RequestParam Long userId,
            @Valid @RequestBody AddressRequestDto requestDto){
        addressService.createAddress(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(AddressSuccessCode.ADDRESS_CREATED));
    }

    // 내 배송지 목록 전체 조회 API
    @GetMapping
    @Operation(summary = "배송지 목록 조회", description = "로그인한 유저의 모든 배송지 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<List<AddressResponseDto>>> getAllAddresses(
            @RequestParam Long userId){
        List<AddressResponseDto> response = addressService.getAllAddresses(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess(AddressSuccessCode.ADDRESS_OK, response));
    }

    // 기존 배송지 정보 수정 API
    @PatchMapping("/{addressId}")
    @Operation(summary = "배송지 수정", description = "기존 배송지 정보를 수정합니다. (소유자 검증 포함)")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS200_2", description = "배송지 정보가 수정되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FORBIDDEN", description = "해당 배송지의 소유자가 아닙니다.")
    })
    public ResponseEntity<ApiResponse<Void>> updateAddress(
            @RequestParam Long userId,
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequestDto requestDto){
        addressService.updateAddress(addressId, userId, requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(AddressSuccessCode.ADDRESS_UPDATED));
    }

    // 등록된 배송지 삭제 API
    @DeleteMapping("/{addressId}")
    @Operation(summary = "배송지 삭제", description = "등록된 배송지를 삭제합니다.")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @RequestParam Long userId,
            @PathVariable Long addressId) {
        addressService.deleteAddress(addressId, userId);
        return ResponseEntity.ok(ApiResponse.onSuccess(AddressSuccessCode.ADDRESS_DELETED));
    }
}
