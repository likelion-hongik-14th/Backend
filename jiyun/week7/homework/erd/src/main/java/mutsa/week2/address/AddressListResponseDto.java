package mutsa.week2.address;

import java.util.List;

public record AddressListResponseDto(
        List<AddressResponseDto> addresses
) {
    public static AddressListResponseDto of(List<AddressResponseDto> addresses) {
        return new AddressListResponseDto(addresses);
    }
}
