package mutsa.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 역직렬화 필수
public class AddressRequestDto {

    @NotBlank(message = "배송지명은 필수입니다.")
    private String addressName;

    @NotBlank(message = "우편번호는 필수입니다.")
    private String zipcode;

    @NotBlank(message = "상세 주소는 필수입니다.")
    private String address;

    @NotBlank(message = "전화번호는 필수입니다.")
    private String phoneNumber;
}
