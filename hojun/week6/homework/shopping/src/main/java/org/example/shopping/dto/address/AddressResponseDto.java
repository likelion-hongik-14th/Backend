package org.example.shopping.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AddressResponseDto {
    private String address;
    private String name;
    private String phone;
    private String zipcode;
}
