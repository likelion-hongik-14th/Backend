package mutsa.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddressRequestDto {
    @NotBlank(message = "Address name must not be blank")
    private String name;

    @NotBlank(message = "Zip code must not be blank")
    private String zipCode;

    @NotBlank(message = "Address must not be blank")
    private String address;

    @NotBlank(message = "Phone number must not be blank")
    private String phoneNumber;
}
