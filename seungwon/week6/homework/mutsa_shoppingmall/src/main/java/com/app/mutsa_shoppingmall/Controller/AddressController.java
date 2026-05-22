package com.app.mutsa_shoppingmall.Controller;

import com.app.mutsa_shoppingmall.DTO.AddressDto;
import com.app.mutsa_shoppingmall.Service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    // 배송지 등록
    @PostMapping
    public ResponseEntity<AddressDto.Response> createAddress(@RequestBody AddressDto.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addressService.createAddress(request));
    }

    // 배송지 전체 조회
    @GetMapping
    public ResponseEntity<AddressDto.ListResponse> getAddresses() {
        return ResponseEntity.ok(addressService.getAddresses());
    }

    // 배송지 수정
    @PatchMapping("/{addressId}")
    public ResponseEntity<AddressDto.Response> updateAddress(
            @PathVariable Long addressId,
            @RequestBody AddressDto.Request request) {
        return ResponseEntity.ok(addressService.updateAddress(addressId, request));
    }
}