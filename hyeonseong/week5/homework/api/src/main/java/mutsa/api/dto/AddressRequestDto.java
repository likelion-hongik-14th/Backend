package mutsa.api.dto;

import lombok.Getter;

@Getter
public class AddressRequestDto {
    private String addressName;
    private String zipcode;
    private String address;
    private String phoneNumber;
}
