package com.study.shop.service;

import com.study.shop.domain.Address;
import com.study.shop.domain.Member;
import com.study.shop.dto.address.AddressRequest;
import com.study.shop.dto.address.AddressResponse;
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
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id=" + memberId));

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
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id=" + memberId));

        return addressRepository.findAllByMemberIdAndDeletedFalse(memberId)
                .stream()
                .map(AddressResponse::new)
                .toList();
    }

    @Transactional
    public AddressResponse updateAddress(Long memberId, Long addressId, AddressRequest request) {
        Address address = addressRepository.findByIdAndMemberIdAndDeletedFalse(addressId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("배송지를 찾을 수 없습니다. id=" + addressId));

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
                .orElseThrow(() -> new IllegalArgumentException("배송지를 찾을 수 없습니다. id=" + addressId));

        address.delete();
    }

}
