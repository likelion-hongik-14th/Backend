package com.study.shop.service;

import com.study.shop.domain.Address;
import com.study.shop.domain.Member;
import com.study.shop.dto.address.AddressRequest;
import com.study.shop.dto.address.AddressResponse;
import com.study.shop.global.apiPayload.code.AddressErrorCode;
import com.study.shop.global.apiPayload.code.MemberErrorCode;
import com.study.shop.global.apiPayload.exception.ProjectException;
import com.study.shop.repository.AddressRepository;
import com.study.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public AddressResponse createAddress(Long memberId, AddressRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ProjectException(MemberErrorCode.MEMBER_NOT_FOUND));


        Address address = new Address(
                member,
                request.getAddressName(),
                request.getZipCode(),
                request.getAddress(),
                request.getDetailAddress(),
                request.getPhoneNumber()
        );

        Address savedAddress = addressRepository.save(address);

        return new AddressResponse(savedAddress);
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAddresses(Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ProjectException(MemberErrorCode.MEMBER_NOT_FOUND));

        return addressRepository.findAllByMemberIdAndDeletedFalse(memberId)
                .stream()
                .map(AddressResponse::new)
                .toList();
    }

    @Transactional
    public AddressResponse updateAddress(Long memberId, Long addressId, AddressRequest request) {
        Address address = addressRepository.findByIdAndMemberIdAndDeletedFalse(addressId, memberId)
                .orElseThrow(() -> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));

        address.update(
                request.getAddressName(),
                request.getZipCode(),
                request.getAddress(),
                request.getDetailAddress(),
                request.getPhoneNumber()
        );

        return new AddressResponse(address);
    }

    @Transactional
    public void deleteAddress(Long memberId, Long addressId) {
        Address address = addressRepository.findByIdAndMemberIdAndDeletedFalse(addressId, memberId)
                .orElseThrow(() -> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));

        address.delete();
    }

}
