package mutsa.session5.Controller;

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
public class AddressController {
    private final AddressService addressService;

    // 배송지 등록
    @PostMapping
    public ResponseEntity<AddressResponseDto> addAddress(@RequestBody AddressRequestDto requestDto) {
        return ResponseEntity.ok(addressService.addAddress(requestDto));
    }

    // 전체 목록 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<AddressResponseDto>> getAddressList(@PathVariable Long memberId) {
        return ResponseEntity.ok(addressService.getAddressList(memberId));
    }

    // 기존 배송지 정보 수정
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> updateAddress(
            @PathVariable Long addressId,
            @RequestBody AddressRequestDto requestDto) {
        return ResponseEntity.ok(addressService.updateAddress(addressId, requestDto));
    }
}
