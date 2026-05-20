package com.app.mutsa_shoppingmall.Service;

import com.app.mutsa_shoppingmall.DTO.AddressDto;
import com.app.mutsa_shoppingmall.Entity.Address;
import com.app.mutsa_shoppingmall.Repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {

    private final AddressRepository addressRepository;

    // 1. 배송지 등록
    @Transactional
    public AddressDto.Response createAddress(AddressDto.Request request) {
        Address address = Address.builder()
                .name(request.getName())
                .zipCode(request.getZipCode())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .build();

        return AddressDto.Response.from(addressRepository.save(address));
    }

    // 2. 배송지 전체 조회
    public AddressDto.ListResponse getAddresses() {
        List<AddressDto.Response> addresses = addressRepository.findAll().stream()
                .map(AddressDto.Response::from)
                .toList();

        return new AddressDto.ListResponse(addresses);
    }

    // 3. 배송지 수정
    @Transactional
    public AddressDto.Response updateAddress(Long addressId, AddressDto.Request request) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("배송지를 찾을 수 없습니다."));

        address.update(request.getName(), request.getZipCode(),
                request.getAddress(), request.getPhoneNumber());

        return AddressDto.Response.from(address);
    }
}