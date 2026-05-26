package mutsa.week2.address;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.week2.common.auth.CurrentMemberProvider;
import mutsa.week2.global.apiPayload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Address", description = "배송지 API")
@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final CurrentMemberProvider currentMember;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "배송지 등록", description = "현재 로그인 회원의 배송지를 등록합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2001", description = "배송지 등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_4001", description = "요청 형식이 올바르지 않습니다.")
    })
    public ApiResponse<AddressResponseDto> create(
            @Valid @RequestBody AddressCreateRequestDto requestDto) {
        return ApiResponse.onSuccess("배송지 등록 성공",
                addressService.create(currentMember.currentMemberId(), requestDto));
    }

    @GetMapping
    @Operation(summary = "내 배송지 목록 조회", description = "현재 로그인 회원의 배송지 목록을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2000", description = "배송지 목록 조회 성공")
    })
    public ApiResponse<AddressListResponseDto> findByMember() {
        return ApiResponse.onSuccess("배송지 목록 조회 성공",
                addressService.findByMember(currentMember.currentMemberId()));
    }

    @PatchMapping("/{addressId}")
    @Operation(summary = "배송지 수정", description = "본인의 배송지 정보를 수정합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2000", description = "배송지 수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS_4031", description = "본인의 배송지만 접근할 수 있습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS_4041", description = "해당 배송지는 존재하지 않습니다.")
    })
    public ApiResponse<AddressResponseDto> update(
            @PathVariable Long addressId,
            @Valid @RequestBody AddressUpdateRequestDto requestDto) {
        return ApiResponse.onSuccess("배송지 수정 성공",
                addressService.update(currentMember.currentMemberId(), addressId, requestDto));
    }
}
