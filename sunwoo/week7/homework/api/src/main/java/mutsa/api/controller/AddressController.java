package mutsa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.AddressRequestDto;
import mutsa.api.dto.AddressResponseDto;
import mutsa.api.global.apiPayload.ApiResponse;
import mutsa.api.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/addresses")
@RequiredArgsConstructor
@Tag(name = "Address", description = "Address API")
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    @Operation(summary = "주소 등록", description = "유저의 배송지 주소를 등록하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "주소 등록에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_4001", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_4041", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<AddressResponseDto>> createAddress(
            @PathVariable Long userId,
            @RequestBody @Valid AddressRequestDto requestDto
    ) {
        AddressResponseDto responseDto = addressService.createAddress(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("주소 등록에 성공했습니다.", responseDto));
    }

    @GetMapping
    @Operation(summary = "주소 목록 조회", description = "유저의 배송지 주소 목록을 조회하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "주소 목록 조회에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_4041", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<List<AddressResponseDto>>> getAddresses(@PathVariable Long userId) {
        List<AddressResponseDto> addresses = addressService.getAddresses(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess("주소 목록 조회에 성공했습니다.", addresses));
    }

    @PatchMapping("/{addressId}")
    @Operation(summary = "주소 수정", description = "유저의 배송지 주소를 수정하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "주소 수정에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_4001", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_4041", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS_4041", description = "주소를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<AddressResponseDto>> updateAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @RequestBody @Valid AddressRequestDto requestDto
    ) {
        AddressResponseDto responseDto = addressService.updateAddress(userId, addressId, requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess("주소 수정에 성공했습니다.", responseDto));
    }

    @DeleteMapping("/{addressId}")
    @Operation(summary = "주소 삭제", description = "유저의 배송지 주소를 삭제하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "주소 삭제에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_4041", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS_4041", description = "주소를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId
    ) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.ok(ApiResponse.onSuccess("주소 삭제에 성공했습니다."));
    }
}
