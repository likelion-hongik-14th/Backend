package mutsa.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.AddressRequestDto;
import mutsa.api.dto.AddressResponseDto;
import mutsa.api.global.apiPayload.ApiResponse;
import mutsa.api.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponseDto>> createAddress(
            @PathVariable Long userId,
            @RequestBody @Valid AddressRequestDto requestDto
    ) {
        AddressResponseDto responseDto = addressService.createAddress(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("주소 등록에 성공했습니다.", responseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponseDto>>> getAddresses(@PathVariable Long userId) {
        List<AddressResponseDto> addresses = addressService.getAddresses(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess("주소 목록 조회에 성공했습니다.", addresses));
    }

    @PatchMapping("/{addressId}")
    public ResponseEntity<ApiResponse<AddressResponseDto>> updateAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @RequestBody @Valid AddressRequestDto requestDto
    ) {
        AddressResponseDto responseDto = addressService.updateAddress(userId, addressId, requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess("주소 수정에 성공했습니다.", responseDto));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId
    ) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.ok(ApiResponse.onSuccess("주소 삭제에 성공했습니다."));
    }
}
