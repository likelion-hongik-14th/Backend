package mutsa.api.controller;

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
public class AddressController {

    private final AddressService addressService;

    // 새로운 배송지 등록 API
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createAddress(@Valid @RequestBody AddressRequestDto requestDto){
        addressService.createAddress(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(AddressSuccessCode.ADDRESS_CREATED));
    }

    // 내 배송지 목록 전체 조회 API
    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponseDto>>> getAllAddresses(){
        List<AddressResponseDto> response = addressService.getAllAddresses();
        return ResponseEntity.ok(ApiResponse.onSuccess(AddressSuccessCode.ADDRESS_OK, response));
    }

    // 기존 배송지 정보 수정 API
    @PatchMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Void>> updateAddress(
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequestDto requestDto){
        addressService.updateAddress(addressId, requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(AddressSuccessCode.ADDRESS_UPDATED));
    }

    // 등록된 배송지 삭제 API
    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.ok(ApiResponse.onSuccess(AddressSuccessCode.ADDRESS_DELETED));
    }
}
