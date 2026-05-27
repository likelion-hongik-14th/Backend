package mutsa.week2.address;

public record AddressResponseDto(
        Long id,
        Long memberId,
        String name,
        String zipCode,
        String address,
        String phone
) {
    public static AddressResponseDto from(Address address) {
        return new AddressResponseDto(
                address.getId(),
                address.getMember().getId(),
                address.getName(),
                address.getZipCode(),
                address.getAddress(),
                address.getPhone()
        );
    }
}
