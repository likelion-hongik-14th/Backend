package mutsa.session5.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.session5.Entity.Address;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponseDto {
    private Long addressId;
    private String addressName;
    private String zipCode;
    private String baseAddress;
    private String detailAddress;
    private String phoneNumber;

    public static AddressResponseDto from(Address address) {
        return AddressResponseDto.builder()
                .addressId(address.getAddressId())
                .addressName(address.getAddressName())
                .zipCode(address.getZipCode())
                .baseAddress(address.getBaseAddress())
                .detailAddress(address.getDetailAddress())
                .phoneNumber(address.getPhoneNumber())
                .build();
    }
}
