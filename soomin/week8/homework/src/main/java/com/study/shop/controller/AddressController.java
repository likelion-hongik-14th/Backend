package com.study.shop.controller;

import com.study.shop.dto.address.AddressRequest;
import com.study.shop.dto.address.AddressResponse;
import com.study.shop.global.apiPayload.ApiResponse;
import com.study.shop.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@Tag(name = "Address", description = "배송지 API")
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "배송지 등록", description = "회원의 배송지를 새로 등록합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "배송지 등록 성공",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "MEMBER404_1 - 회원을 찾을 수 없습니다.",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponse>> createAddress(
            @RequestParam Long memberId,
            @Valid @RequestBody AddressRequest request
            ) {
        AddressResponse response = addressService.createAddress(memberId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess("배송지 등록 성공" , response));
    }

    @Operation(summary = "배송지 목록 조회", description = "memberId를 기준으로 삭제되지 않은 배송지 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "배송지 목록 조회 성공",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "MEMBER404_1 - 회원을 찾을 수 없습니다.",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponse>>> getAddresses(@RequestParam Long memberId) {
        List<AddressResponse> response = addressService.getAddresses(memberId);

        return ResponseEntity
                .ok(ApiResponse.onSuccess("배송지 목록 조회 성공" , response));
    }

    @Operation(summary = "배송지 수정", description = "addressId를 기준으로 배송지 정보를 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "배송지 수정 성공",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "ADDRESS404_1 - 배송지를 찾을 수 없습니다.",
                    content = @Content(mediaType = "application/json"))
    })
    @PatchMapping("/{addressId}")
    public ResponseEntity<ApiResponse<AddressResponse>> updateAddress(
            @RequestParam Long memberId,
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequest request
    ) {
        AddressResponse response = addressService.updateAddress(memberId, addressId, request);

        return ResponseEntity
                .ok(ApiResponse.onSuccess("배송지 수정 성공" , response));
    }

    @Operation(summary = "배송지 삭제", description = "배송지를 실제 삭제하지 않고 삭제 상태로 변경합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "배송지 삭제 성공",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "ADDRESS404_1 - 배송지를 찾을 수 없습니다.",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @RequestParam Long memberId,
            @PathVariable Long addressId
    ) {
        addressService.deleteAddress(memberId, addressId);

        return ResponseEntity
                .ok(ApiResponse.onSuccess("배송지 삭제 성공"));
    }

}
