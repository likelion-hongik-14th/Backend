package mutsa.hw5.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.hw5.dto.address.AddressRequestDto;
import mutsa.hw5.dto.address.AddressResponseDto;
import mutsa.hw5.dto.address.AddressUpdateDto;
import mutsa.hw5.global.apiPayload.ApiResponse;
import mutsa.hw5.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/addresses")
@Tag(name = "Address", description = "배송지 관련 API")
public class AddressController {

    private final AddressService addressService;

    // 배송지 등록
    @Operation(summary = "배송지 등록", description = "새로운 배송지를 등록합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "배송지 등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER404_1", description = "회원을 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS400_1", description = "배송지는 20개를 초과할 수 없습니다.")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponseDto>> createAddress(@Valid @RequestBody AddressRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("배송지가 등록되었습니다.", addressService.createAddress(dto)));
    }

    // 배송지 목록 조회
    @Operation(summary = "배송지 목록 조회", description = "회원님의 배송지 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "배송지 목록 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER404_1", description = "회원을 찾을 수 없습니다.")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponseDto>>> getAddresses(@RequestParam Long memberId) {
        return ResponseEntity.ok(ApiResponse.onSuccess("배송지 목록 조회 성공", addressService.getAddresses(memberId)));
    }

    // 배송지 수정
    @Operation(summary = "배송지 수정", description = "특정 배송지의 정보를 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "배송지 수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS404_1", description = "배송지를 찾을 수 없습니다.")
    })
    @PatchMapping("/{addressId}")
    public ResponseEntity<ApiResponse<AddressResponseDto>> updateAddress(
            @PathVariable Long addressId,
            @RequestParam Long memberId,
            @Valid @RequestBody AddressUpdateDto dto) {
        return ResponseEntity.ok(ApiResponse.onSuccess("배송지가 수정되었습니다.", addressService.updateAddress(addressId, memberId, dto)));
    }

    // 배송지 삭제
    @Operation(summary = "배송지 삭제", description = "특정 배송지를 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "배송지 삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS404_1", description = "배송지를 찾을 수 없습니다.")
    })
    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @PathVariable Long addressId,
            @RequestParam Long memberId) {
        addressService.deleteAddress(addressId, memberId);
        return ResponseEntity.ok(ApiResponse.onSuccess("배송지가 삭제되었습니다."));
    }
}
