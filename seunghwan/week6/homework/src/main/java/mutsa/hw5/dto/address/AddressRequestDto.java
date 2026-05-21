package mutsa.hw5.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import mutsa.hw5.domain.Address;
import mutsa.hw5.domain.Member;

@Getter
public class AddressRequestDto {

    @NotNull // null만 막음. 빈 문자열 "" 은 통과(숫자 타입은 빈 문자열 개념x, 그래서 @NotBlank 못 씀)
    private Long memberId;

    @NotBlank // null이랑 빈 문자열 "" 둘 다 막음
    private String receiverName;

    @NotBlank
    private String addressName;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;

    // DTO → Entity 변환
    // Service에서 호출하지만, 변환 로직을 DTO에 숨겨두어서 깔끔하게 유지
    public Address toEntity(Member member) {
        return Address.create(member, this.receiverName, this.addressName, this.postalCode, this.address, this.phoneNumber);
    }
}
