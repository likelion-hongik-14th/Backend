package mutsa.hw5.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.hw5.dto.address.AddressRequestDto;
import mutsa.hw5.dto.address.AddressResponseDto;
import mutsa.hw5.dto.address.AddressUpdateDto;
import mutsa.hw5.global.apiPayload.ApiResponse;
import mutsa.hw5.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    // 배송지 등록
    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponseDto>> createAddress(@Valid @RequestBody AddressRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("배송지가 등록되었습니다.", addressService.createAddress(dto)));
    }

    // 배송지 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponseDto>>> getAddresses(@RequestParam Long memberId) {
        return ResponseEntity.ok(ApiResponse.onSuccess("배송지 목록 조회 성공", addressService.getAddresses(memberId)));
    }

    // 배송지 수정
    @PatchMapping("/{addressId}")
    public ResponseEntity<ApiResponse<AddressResponseDto>> updateAddress(
            @PathVariable Long addressId,
            @RequestParam Long memberId,
            @Valid @RequestBody AddressUpdateDto dto) {
        return ResponseEntity.ok(ApiResponse.onSuccess("배송지가 수정되었습니다.", addressService.updateAddress(addressId, memberId, dto)));
    }

    // 배송지 삭제
    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @PathVariable Long addressId,
            @RequestParam Long memberId) {
        addressService.deleteAddress(addressId, memberId);
        return ResponseEntity.ok(ApiResponse.onSuccess("배송지가 삭제되었습니다."));
    }
}
