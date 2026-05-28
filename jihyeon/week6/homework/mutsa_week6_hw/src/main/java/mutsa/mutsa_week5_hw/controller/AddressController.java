package mutsa.mutsa_week5_hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.dto.AddressRequestDto;
import mutsa.mutsa_week5_hw.dto.AddressUpdateDto;
import mutsa.mutsa_week5_hw.dto.AddressResponseDto;
import mutsa.mutsa_week5_hw.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    // 배송지 등록
    @PostMapping
    public AddressResponseDto createAddress(
            @RequestParam Long memberId,
            @RequestBody @Valid AddressRequestDto dto
    ) {

        return addressService.createAddress(memberId, dto);
    }


    // 배송지 전체 조회
    @GetMapping
    public List<AddressResponseDto> getAddresses(
            @RequestParam Long memberId
    ) {

        return addressService.getAddresses(memberId);
    }


    // 배송지 수정
    @PatchMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> updateAddress(
            @RequestParam Long memberId,  // ✅ 추가
            @PathVariable Long addressId,
            @RequestBody @Valid AddressUpdateDto dto) {

        return ResponseEntity.ok(
                addressService.updateAddress(memberId, addressId, dto)
        );
    }


    // 배송지 삭제
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @RequestParam Long memberId,
            @PathVariable Long addressId) {

        addressService.deleteAddress(memberId, addressId);
        return ResponseEntity.noContent().build();
    }

}
