package mutsa.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.api.domain.Address;

@NoArgsConstructor
@Getter
public class AddressResponseDto {
    private Long id;
    private String name;
    private String zipCode;
    private String address;
    private String phoneNumber;

    public static AddressResponseDto from(Address address) {
        AddressResponseDto responseDto = new AddressResponseDto();
        responseDto.id = address.getId();
        responseDto.name = address.getName();
        responseDto.zipCode = address.getZip_code();
        responseDto.address = address.getAddress();
        responseDto.phoneNumber = address.getPhone_number();
        return responseDto;
    }
}
