package mutsa.shop.dto.addressDto;

import lombok.*;
import mutsa.shop.domain.Address;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AddressResponseDto {
    private Long id;
    private Long memberId;
    private String name;
    private String address;
    private String phone;
    private String postalCode;

    public static AddressResponseDto from(Address address) {
        return AddressResponseDto.builder()
                .id(address.getId())
                .memberId(address.getMember().getId()) // 엔티티에서 회원 ID 추출
                .name(address.getName())
                .postalCode(address.getPostalcode())
                .address(address.getAddress())
                .phone(address.getPhone())
                .build();
    }

}
