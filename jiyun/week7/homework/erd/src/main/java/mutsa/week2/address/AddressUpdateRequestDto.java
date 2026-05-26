package mutsa.week2.address;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressUpdateRequestDto {

    @NotBlank(message = "name은 필수입니다.")
    private String name;

    @NotBlank(message = "zipCode는 필수입니다.")
    private String zipCode;

    @NotBlank(message = "address는 필수입니다.")
    private String address;

    @NotBlank(message = "phone은 필수입니다.")
    private String phone;
}
