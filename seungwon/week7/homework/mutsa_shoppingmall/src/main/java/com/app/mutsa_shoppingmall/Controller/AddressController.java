package com.app.mutsa_shoppingmall.Controller;

import com.app.mutsa_shoppingmall.DTO.AddressDto;
import com.app.mutsa_shoppingmall.Service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Address", description = "배송지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "배송지 등록", description = "새로운 배송지를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "등록 성공")
    })
    @PostMapping
    public ResponseEntity<AddressDto.Response> createAddress(@RequestBody AddressDto.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addressService.createAddress(request));
    }

    @Operation(summary = "배송지 전체 조회", description = "등록된 모든 배송지를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public ResponseEntity<AddressDto.ListResponse> getAddresses() {
        return ResponseEntity.ok(addressService.getAddresses());
    }

    @Operation(summary = "배송지 수정", description = "특정 배송지 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "ADDRESS_4041 - 배송지를 찾을 수 없습니다.")
    })
    @PatchMapping("/{addressId}")
    public ResponseEntity<AddressDto.Response> updateAddress(
            @PathVariable Long addressId,
            @RequestBody AddressDto.Request request) {
        return ResponseEntity.ok(addressService.updateAddress(addressId, request));
    }
}