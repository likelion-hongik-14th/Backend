package mutsa.shop.controller;

import lombok.RequiredArgsConstructor;
import mutsa.shop.dto.addressDto.AddressRequestDto;
import mutsa.shop.dto.addressDto.AddressResponseDto;
import mutsa.shop.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponseDto> addAddress(
            @RequestHeader("X-Member-Id") Long memberId, // 테스트를 위해 헤더로 회원 ID를 받음
            @RequestBody AddressRequestDto requestDto) {

        AddressResponseDto responseDto = addressService.addAddress(requestDto, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> getAddresses(
            @RequestHeader("X-Member-Id") Long memberId) {

        List<AddressResponseDto> addresses = addressService.getAddresses(memberId);
        return ResponseEntity.ok(addresses); // 200 OK와 함께 목록 반환
    }

    @PatchMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> updateAddress(
            @PathVariable("addressId") Long addressId,
            @RequestBody AddressRequestDto requestDto) {

        AddressResponseDto responseDto = addressService.updateAddress(addressId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("addressId") Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build(); // 응답 바디 없이 204 상태코드만 반환
    }
}
