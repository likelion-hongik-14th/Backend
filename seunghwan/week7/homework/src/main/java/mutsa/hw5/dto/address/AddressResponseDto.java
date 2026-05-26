package mutsa.hw5.dto.address;

import lombok.Builder;
import lombok.Getter;
import mutsa.hw5.domain.Address;

@Getter
@Builder
public class AddressResponseDto {

    private Long addressId;
    private String receiverName;
    private String addressName;
    private String postalCode;
    private String address;
    private String phoneNumber;

    // Entity → DTO 변환
    // Service에서 호출하지만, 변환 로직을 DTO에 숨겨두어서 깔끔하게 유지
    public static AddressResponseDto from(Address address) {
        return AddressResponseDto.builder()
                .addressId(address.getAddressId())
                .receiverName(address.getReceiverName())
                .addressName(address.getAddressName())
                .postalCode(address.getPostalCode())
                .address(address.getAddress())
                .phoneNumber(address.getPhoneNumber())
                .build();
    }
}
