package mutsa.w5homework.controller;

import lombok.RequiredArgsConstructor;
import mutsa.w5homework.dto.AddressDto;
import mutsa.w5homework.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/addresses")
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDto.Response> createAddress(@RequestBody AddressDto.CreateRequest dto) {
        AddressDto.Response response = addressService.createAddress(dto);
        return ResponseEntity.ok(response);
    }

    //특정 회원의 배송지 조회, (GET /v1/addresses?memberId=1)
    @GetMapping
    public ResponseEntity<List<AddressDto.Response>> getAllAddress(@RequestParam Long memberId) {
        List<AddressDto.Response> responses = addressService.getAllAddress(memberId);
        return ResponseEntity.ok(responses);
    }

    //배송지 수정
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDto.Response> updateAddress(@PathVariable Long addressId, @RequestBody AddressDto.UpdateRequest dto) {
        AddressDto.Response response = addressService.updateAddress(addressId, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<AddressDto.Response> deleteAddress(@PathVariable Long addressId) {
        AddressDto.Response response = addressService.deleteAddress(addressId);
        return ResponseEntity.ok(response);
    }
}
