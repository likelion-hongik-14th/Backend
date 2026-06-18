package mutsa.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
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
@Tag(name = "Address", description = "Address API")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    @Operation(summary = "주소 등록", description = "주소 등록을 할 때 사용하는 API")
    public ResponseEntity<mutsa.shop.global.apiPayload.ApiResponse<AddressResponseDto>> addAddress(
            @RequestHeader("X-Member-Id") Long memberId,
            @RequestBody AddressRequestDto requestDto) {

        AddressResponseDto responseDto = addressService.addAddress(requestDto, memberId);

        // 공통 응답 포맷 패키지를 명시적으로 지정하여 성공 데이터 매핑
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mutsa.shop.global.apiPayload.ApiResponse.onSuccess("주소 등록에 성공하였습니다.", responseDto));
    }


    @GetMapping
    @Operation(summary = "주소 목록 조회", description = "등록한 주소 목록을 조회하기 위한 API")
    public ResponseEntity<mutsa.shop.global.apiPayload.ApiResponse<List<AddressResponseDto>>> getAddresses(
            @RequestHeader("X-Member-Id") Long memberId) {

        List<AddressResponseDto> addresses = addressService.getAddresses(memberId);

        return ResponseEntity.ok(mutsa.shop.global.apiPayload.ApiResponse.onSuccess("주소 목록 조회에 성공하였습니다.", addresses));
    }


    @Transactional
    @PatchMapping("/{addressId}")
    @Operation(summary = "주소 정보 수정", description = "등록한 주소의 정보를 수정하기 위한 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON2000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "ADDRESS4041", description = "해당 배송지를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<mutsa.shop.global.apiPayload.ApiResponse<AddressResponseDto>> updateAddress(
            @PathVariable("addressId") Long addressId,
            @RequestBody AddressRequestDto requestDto) {

        AddressResponseDto responseDto = addressService.updateAddress(addressId, requestDto);

        return ResponseEntity.ok(mutsa.shop.global.apiPayload.ApiResponse.onSuccess("주소 정보 수정에 성공하였습니다.", responseDto));
    }


    @DeleteMapping("/{addressId}")
    @Operation(summary = "주소 삭제", description = "등록된 주소 목록 삭제시 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON2000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "ADDRESS4041", description = "해당 배송지를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<mutsa.shop.global.apiPayload.ApiResponse<Void>> deleteAddress(@PathVariable("addressId") Long addressId) {
        addressService.deleteAddress(addressId);


        return ResponseEntity.ok(mutsa.shop.global.apiPayload.ApiResponse.onSuccess("주소 삭제에 성공하였습니다."));
    }
}