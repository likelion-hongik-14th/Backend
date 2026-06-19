package mutsa.hw5.dto.address;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AddressUpdateDto {

    // null이면 수정 안 함, 값이 있으면 빈 문자열 방지
    @Size(min = 1)
    private String receiverName;
    @Size(min = 1)
    private String addressName;
    @Size(min = 1)
    private String postalCode;
    @Size(min = 1)
    private String address;
    @Size(min = 1)
    private String phoneNumber;
}
