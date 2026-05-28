package mutsa.mutsa_week5_hw.dto;

import lombok.Builder;
import lombok.Getter;
import mutsa.mutsa_week5_hw.domain.Address;

// 배송지 조회(응답) -> 배송지 목록 및 상세 조회 시, 식별자를 포함한 주소 정보를 클라이언트에 제공
@Getter
@Builder
public class AddressResponseDto {

    private Long id;
    private String name;
    private String zipcode;
    private String address;
    private String phone;

    public static AddressResponseDto from(Address address) {

        return AddressResponseDto.builder()
                .id(address.getId())
                .name(address.getName())
                .zipcode(address.getZipcode())
                .address(address.getAddress())
                .phone(address.getPhone())
                .build();
    }

}
