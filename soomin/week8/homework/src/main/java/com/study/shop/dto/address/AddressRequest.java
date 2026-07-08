package com.study.shop.dto.address;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AddressRequest {

    @NotBlank(message = "배송지명은 필수입니다.")
    private String addressName;

    @NotBlank(message = "우편번호는 필수입니다.")
    private String zipCode;

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    @NotBlank(message = "상세주소는 필수입니다.")
    private String detailAddress;

    @NotBlank(message = "전화번호는 필수입니다.")
    private String phoneNumber;

}
