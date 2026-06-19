package org.example.shopping.dto.address;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressRequestDto {
    private String address;
    private String zipcode;
    private String phone;
    private String name;
}
