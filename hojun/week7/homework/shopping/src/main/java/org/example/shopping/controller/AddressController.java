package org.example.shopping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.address.AddressRequestDto;
import org.example.shopping.dto.address.AddressResponseDto;
import org.example.shopping.global.apiPayload.ApiResponse;
import org.example.shopping.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Address API", description = "주소 도메인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AddressController {
    private final AddressService addressService;

    @Operation(summary = "주소 추가", description = "주소의 상세정보 받아서 주소를 추가한다")
    @PostMapping("/{userId}/addresses")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CREATED", description = "주소 추가가 완료되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_NOT_FOUND", description = "존재하지 않는 회원입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BAD_REQUEST", description = "잘못된 요청입니다. (유효성 검증 실패)", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<Long>> addAddress(@PathVariable Long userId, @RequestBody AddressRequestDto request) {
        Long addressId = addressService.createAddress(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("주소 추가가 완료되었습니다", addressId));
    }

    @Operation(summary = "유저 주소 조회", description = "사용자 아이디를 받아서 주소 목록을 조회한다")
    @GetMapping("/{userId}/addresses")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SUCCESS", description = "주소 목록 조회가 완료되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_NOT_FOUND", description = "존재하지 않는 회원입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS_NOT_FOUND", description = "배송지 정보를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<List<AddressResponseDto>>> getAddressList(@PathVariable Long userId) {
        List<AddressResponseDto> addresses =  addressService.getAddressList(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess("주소 목록 조회가 완료되었습니다", addresses));
    }

    @Operation(summary = "주소 변경")
    @PatchMapping("/{userId}/addresses/{addressId}")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SUCCESS", description = "주소가 업데이트 되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_NOT_FOUND", description = "존재하지 않는 회원입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS_NOT_FOUND", description = "존재하지 않는 배송지 정보입니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<Void>> updateAddress(@PathVariable Long userId, @PathVariable Long addressId, @RequestBody AddressRequestDto request) {
        addressService.updateAddress(userId, addressId, request);
        return ResponseEntity.ok(ApiResponse.onSuccess("주소가 업데이트 되었습니다."));
    }

    @Operation(summary = "주소 삭제")
    @DeleteMapping("/{userId}/addresses/{addressId}")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SUCCESS", description = "배송지 정보가 성공적으로 삭제되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_NOT_FOUND", description = "존재하지 않는 회원입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS_NOT_FOUND", description = "존재하지 않는 배송지 정보입니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<Void>> deleteAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.ok(ApiResponse.onSuccess("배송지 정보가 성공적으로 삭제되었습니다."));
    }
}
