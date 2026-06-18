package mutsa.session5.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.AddressRequestDto;
import mutsa.session5.Dto.AddressResponseDto;
import mutsa.session5.Service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@Tag(name = "배송지(Address) API", description = "회원별 다중 배송지 등록, 조회, 수정 API")
public class AddressController {
    private final AddressService addressService;

    // 배송지 등록
    @PostMapping
    @Operation(summary = "배송지 추가 등록", description = "회원 ID와 매핑하여 새로운 배송지 주소 정보를 시스템에 저장")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "배송지가 성공적으로 등록되었습니다.",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 회원입니다. (MEMBER_404_1)",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<AddressResponseDto> addAddress(@RequestBody AddressRequestDto requestDto) {
        return ResponseEntity.ok(addressService.addAddress(requestDto));
    }

    // 전체 목록 조회
    @GetMapping("/member/{memberId}")
    @Operation(summary = "회원 배송지 목록 조회", description = "특정 회원이 보유한 전체 배송지 리스트를 한번에 받음")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "배송지 목록 조회가 완료되었습니다.",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 회원입니다. (MEMBER_404_1)",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<List<AddressResponseDto>> getAddressList(@PathVariable Long memberId) {
        return ResponseEntity.ok(addressService.getAddressList(memberId));
    }

    // 기존 배송지 정보 수정
    @PutMapping("/{addressId}")
    @Operation(summary = "배송지 주소 정보 변경", description = "기존 배송지 ID를 받아 해당 주소의 상세 내역을 새로 업데이트")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "배송지 정보 수정이 완료되었습니다.",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "수정할 배송지 정보를 찾을 수 없습니다. (ORDER_404_2)",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<AddressResponseDto> updateAddress(
            @PathVariable Long addressId,
            @RequestBody AddressRequestDto requestDto) {
        return ResponseEntity.ok(addressService.updateAddress(addressId, requestDto));
    }
}
