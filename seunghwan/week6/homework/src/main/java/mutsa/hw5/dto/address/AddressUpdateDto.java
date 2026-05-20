package mutsa.hw5.dto.address;

import lombok.Getter;

@Getter
public class AddressUpdateDto {

    // null이면 수정 안 함
    private String addressName;
    private String postalCode;
    private String address;
    private String phoneNumber;
}
