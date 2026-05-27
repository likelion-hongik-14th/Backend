package mutsa.homework.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.homework.controller.docs.AddressApiDocs;
import mutsa.homework.dto.address.AddAddressRequestDto;
import mutsa.homework.dto.address.AddressResponseDto;
import mutsa.homework.dto.address.UpdateAddressRequestDto;
import mutsa.homework.global.apiPayload.GlobalResponse;
import mutsa.homework.global.dto.ListResponseDto;
import mutsa.homework.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController implements AddressApiDocs {

    private final AddressService addressService;

    @Override
    @PostMapping
    public ResponseEntity<GlobalResponse<AddressResponseDto>> createAddress(
            @RequestHeader("X-User-Id") Long userId,
            @Valid
            @RequestBody AddAddressRequestDto requestDto
    ) {
        AddressResponseDto responseDto = addressService.addAddress(userId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.onSuccessCreate(responseDto));
    }

    @Override
    @GetMapping("/{addressId}")
    public ResponseEntity<GlobalResponse<AddressResponseDto>> getAddress(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("addressId") Long addressId
    ) {
        AddressResponseDto responseDto = addressService.getAddress(userId, addressId);

        return ResponseEntity.ok(GlobalResponse.onSuccess(responseDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<GlobalResponse<ListResponseDto<AddressResponseDto>>> getAddressList(
            @RequestHeader("X-User-Id") Long userId
    ) {
        ListResponseDto<AddressResponseDto> responseDto = addressService.getAllAddress(userId);

        return ResponseEntity.ok(GlobalResponse.onSuccess(responseDto));
    }

    @Override
    @PutMapping("/{addressId}")
    public ResponseEntity<GlobalResponse<AddressResponseDto>> updateAddress(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("addressId") Long addressId,
            @Valid
            @RequestBody UpdateAddressRequestDto requestDto
    ) {
        AddressResponseDto responseDto = addressService.updateAddress(userId, addressId, requestDto);

        return ResponseEntity.ok(GlobalResponse.onSuccess(responseDto));
    }

    @Override
    @DeleteMapping("/{addressId}")
    public ResponseEntity<GlobalResponse<Void>> deleteAddress(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("addressId") Long addressId
    ) {
        addressService.deleteAddress(userId, addressId);

        return ResponseEntity.ok(GlobalResponse.onSuccess());
    }
}
