package com.study.shop.controller;

import com.study.shop.dto.address.AddressRequest;
import com.study.shop.dto.address.AddressResponse;
import com.study.shop.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(
            @RequestParam Long memberId,
            @Valid @RequestBody AddressRequest request
            ) {
        AddressResponse response = addressService.createAddress(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAddresses(@RequestParam Long memberId) {
        return ResponseEntity.ok(addressService.getAddresses(memberId));
    }

    @PatchMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(
            @RequestParam Long memberId,
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequest request
    ) {
        return ResponseEntity.ok(addressService.updateAddress(memberId, addressId, request));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @RequestParam Long memberId,
            @PathVariable Long addressId
    ) {
        addressService.deleteAddress(memberId, addressId);
        return ResponseEntity.noContent().build();
    }

}
