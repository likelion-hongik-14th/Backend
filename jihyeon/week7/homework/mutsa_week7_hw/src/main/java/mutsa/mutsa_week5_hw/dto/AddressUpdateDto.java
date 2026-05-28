package mutsa.mutsa_week5_hw.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

// 배송지 수정(요청) -> 배송지 정보 수정 시, 변경 불가능한 회원 ID를 제외한 수정 가능한 주소 정보만 수령
@Getter
@NoArgsConstructor
public class AddressUpdateDto {

    private String name;
    private String zipcode;
    private String address;
    private String phone;

}
