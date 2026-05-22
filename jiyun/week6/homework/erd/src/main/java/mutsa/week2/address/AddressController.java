package mutsa.week2.address;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.week2.common.auth.CurrentMemberProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final CurrentMemberProvider currentMember;

    @PostMapping
    public ResponseEntity<AddressResponseDto> create(
            @Valid @RequestBody AddressCreateRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addressService.create(currentMember.currentMemberId(), requestDto));
    }

    @GetMapping
    public ResponseEntity<AddressListResponseDto> findByMember() {
        return ResponseEntity.ok(addressService.findByMember(currentMember.currentMemberId()));
    }

    @PatchMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> update(
            @PathVariable Long addressId,
            @Valid @RequestBody AddressUpdateRequestDto requestDto
    ) {
        return ResponseEntity.ok(
                addressService.update(currentMember.currentMemberId(), addressId, requestDto));
    }
}
