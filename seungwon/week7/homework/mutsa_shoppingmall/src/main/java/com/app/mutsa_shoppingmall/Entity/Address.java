package com.app.mutsa_shoppingmall.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // 배송지명 (집, 회사 등)
    private String zipCode;     // 우편번호
    private String address;     // 주소
    private String phoneNumber; // 전화번호

    public void update(String name, String zipCode, String address, String phoneNumber) {
        this.name = name;
        this.zipCode = zipCode;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}