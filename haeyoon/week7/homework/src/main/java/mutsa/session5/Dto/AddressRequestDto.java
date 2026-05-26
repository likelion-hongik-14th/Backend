package mutsa.session5.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "주소 이름은 필수입니다.")
    private String addressName;

    @NotBlank(message = "우편번호는 필수입니다.")
    @Pattern(regexp = "\\d{5}", message = "우편번호 형식이 올바르지 않습니다.")
    private String zipCode;

    @NotBlank(message = "주소는 필수입니다.")
    private String baseAddress;

    @NotBlank(message = "상세 주소는 필수입니다.")
    private String detailAddress;

    @NotBlank(message =  "전화번호는 필수입니다.")
    private String phoneNumber;
}
