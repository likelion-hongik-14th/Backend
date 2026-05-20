package mutsa.mutsa_week5_hw.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 배송지 등록(요청) -> 신규 배송지 등록 시, 입력 받은 배송지명, 주소, 우편 번호, 연락처 및 소유자 ID 데이터를 수집
@Getter
@NoArgsConstructor
public class AddressRequestDto {

    @NotBlank(message = "배송지명은 필수 입력사항입니다.")
    private String name;

    @NotBlank(message = "우편번호는 필수 입력사항입니다.")
    private String zipcode;

    @NotBlank(message = "주소는 필수 입력사항입니다.")
    private String address;

    @NotBlank(message = "전화번호는 필수 입력사항입니다.")
    private String phone;

}
