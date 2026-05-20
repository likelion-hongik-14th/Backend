package mutsa.homework.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.homework.dto.address.AddAddressRequestDto;
import mutsa.homework.dto.address.AddressResponseDto;
import mutsa.homework.dto.address.UpdateAddressRequestDto;
import mutsa.homework.global.dto.ApiResponse;
import mutsa.homework.global.dto.ListResponseDto;
import mutsa.homework.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponseDto>> createAddress(
            @RequestHeader("X-User-Id") Long userId,
            @Valid
            @RequestBody AddAddressRequestDto requestDto
    ) {
        AddressResponseDto responseDto = addressService.addAddress(userId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(responseDto));
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<ApiResponse<AddressResponseDto>> getAddress(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("addressId") Long addressId
    ) {
        AddressResponseDto responseDto = addressService.getAddress(userId, addressId);

        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ListResponseDto<AddressResponseDto>>> getAddressList(
            @RequestHeader("X-User-Id") Long userId
    ) {
        ListResponseDto<AddressResponseDto> responseDto = addressService.getAllAddress(userId);

        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<ApiResponse<AddressResponseDto>> updateAddress(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("addressId") Long addressId,
            @Valid
            @RequestBody UpdateAddressRequestDto requestDto
    ) {
        AddressResponseDto responseDto = addressService.updateAddress(userId, addressId, requestDto);

        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("addressId") Long addressId
    ) {
        addressService.deleteAddress(userId, addressId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
