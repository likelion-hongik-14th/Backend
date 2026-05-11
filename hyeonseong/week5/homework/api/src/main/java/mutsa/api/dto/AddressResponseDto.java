package mutsa.api.dto;

import lombok.Getter;
import mutsa.api.domain.Address;

@Getter
public class AddressResponseDto {
    private Long id;
    private String addressName;
    private String zipcode;
    private String address;
    private String phoneNumber;

    public AddressResponseDto(Address address){
        this.id = address.getId();
        this.addressName = address.getAddressName();
        this.zipcode = address.getZipcode();
        this.address = address.getAddress();
        this.phoneNumber = address.getPhoneNumber();
    }
}
