package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.address.AddressRequestDto;
import org.example.shopping.dto.address.AddressResponseDto;
import org.example.shopping.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/{userId}/addresses")
    public ResponseEntity<Long> addAddress(@PathVariable Long userId, @RequestBody AddressRequestDto request) {
        Long addressId = addressService.createAddress(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(addressId);
    }

    @GetMapping("/{userId}/addresses")
    public ResponseEntity<List<AddressResponseDto>> getAddressList(@PathVariable Long userId) {
        List<AddressResponseDto> addresses =  addressService.getAddressList(userId);
        return ResponseEntity.ok(addresses);
    }

    @PatchMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<Void> updateAddress(@PathVariable Long userId, @PathVariable Long addressId, @RequestBody AddressRequestDto request) {
        addressService.updateAddress(userId, addressId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.noContent().build();
    }
}
