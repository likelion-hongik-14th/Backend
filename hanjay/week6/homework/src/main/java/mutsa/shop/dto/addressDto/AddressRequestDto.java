package mutsa.shop.dto.addressDto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AddressRequestDto {
    private String name;
    private String address;
    private String phone;
    private String postalcode;
}
