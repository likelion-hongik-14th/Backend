package com.study.shop.dto.address;

import com.study.shop.domain.Address;
import lombok.Getter;

@Getter
public class AddressResponse {

    private Long addressId;
    private Long memberId;
    private String addressName;
    private String zipCode;
    private String address;
    private String detailAddress;
    private String phoneNumber;

    public AddressResponse(Address address) {
        this.addressId = address.getId();
        this.memberId = address.getMember().getId();
        this.addressName = address.getAddressName();
        this.zipCode = address.getZipCode();
        this.address = address.getAddress();
        this.detailAddress = address.getDetailAddress();
        this.phoneNumber = address.getPhoneNumber();
    }

}
