package mutsa.mutsa_week5_hw.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Address", description = "주소 API")
public class AddressController {

    private final AddressService addressService;

    // 배송지 등록
    @Operation(summary = "배송지 등록", description = "배송지를 신규 등록할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "404", description = "MEMBER_NOT_FOUND"),
            @ApiResponse(responseCode = "409", description = "ADDRESS_ALREADY_EXISTS")
    })
    @PostMapping
    public AddressResponseDto createAddress(
            @RequestParam Long memberId,
            @RequestBody @Valid AddressRequestDto dto
    ) {

        return addressService.createAddress(memberId, dto);
    }


    // 배송지 전체 조회
    @Operation(summary = "전체 배송지 목록 조회", description = "등록한 전체 배송지 목록을 조회할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "MEMBER_NOT_FOUND / ADDRESS_NOT_FOUND")
    })
    @GetMapping
    public List<AddressResponseDto> getAddresses(
            @RequestParam Long memberId
    ) {

        return addressService.getAddresses(memberId);
    }


    // 배송지 수정
    @Operation(summary = "배송지 정보 수정", description = "이미 등록한 배송지의 정보를 수정할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "ADDRESS_NOT_FOUND"),
            @ApiResponse(responseCode = "403", description = "ADDRESS_FORBIDDEN"),
            @ApiResponse(responseCode = "400", description = "ADDRESS_NO_CHANGES")
    })
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
    @Operation(summary = "배송지 삭제", description = "이미 등록한 배송지를 삭제할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "ADDRESS_NOT_FOUND"),
            @ApiResponse(responseCode = "403", description = "ADDRESS_FORBIDDEN")
    })
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @RequestParam Long memberId,
            @PathVariable Long addressId) {

        addressService.deleteAddress(memberId, addressId);
        return ResponseEntity.noContent().build();
    }

}
