package org.example.shopping.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressRequestDto {

    @NotBlank(message = "배송 주소는 필수 입력 값입니다.")
    private String address;

    @NotBlank(message = "우편번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^\\d{5}$", message = "우편번호는 5자리 숫자여야 합니다.")
    private String zipcode;

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "올바른 전화번호 형식(010-XXXX-XXXX)이어야 합니다.")
    private String phone;

    @NotBlank(message = "주소 별칭은 필수 입력 값입니다.")
    private String name;
}
