package mutsa.api.dto;

import lombok.Builder;
import lombok.Getter;
import mutsa.api.domain.Address;

@Getter
@Builder
public class AddressResponseDto {
    private Long id;
    private String addressName;
    private String zipcode;
    private String address;
    private String phoneNumber;

    public static AddressResponseDto of(Address address){
        return AddressResponseDto.builder()
                .id(address.getId())
                .addressName(address.getAddressName())
                .zipcode(address.getZipcode())
                .address(address.getAddress())
                .phoneNumber(address.getPhoneNumber())
                .build();
    }
}
