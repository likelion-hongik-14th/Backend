package com.app.mutsa_shoppingmall.DTO;

import com.app.mutsa_shoppingmall.Entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class AddressDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String name;
        private String zipCode;
        private String address;
        private String phoneNumber;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String name;
        private String zipCode;
        private String address;
        private String phoneNumber;

        public static Response from(Address address) {
            return Response.builder()
                    .id(address.getId())
                    .name(address.getName())
                    .zipCode(address.getZipCode())
                    .address(address.getAddress())
                    .phoneNumber(address.getPhoneNumber())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ListResponse {
        private List<Response> addresses;
    }
}