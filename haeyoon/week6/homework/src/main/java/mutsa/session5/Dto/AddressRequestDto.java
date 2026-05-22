package mutsa.session5.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequestDto {
    private Long memberId;
    private String addressName;
    private String zipCode;
    private String baseAddress;
    private String detailAddress;
    private String phoneNumber;
}
