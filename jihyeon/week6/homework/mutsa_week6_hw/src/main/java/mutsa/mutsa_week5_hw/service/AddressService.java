package mutsa.mutsa_week5_hw.service;

import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.domain.Address;
import mutsa.mutsa_week5_hw.domain.Member;
import mutsa.mutsa_week5_hw.dto.AddressRequestDto;
import mutsa.mutsa_week5_hw.dto.AddressResponseDto;
import mutsa.mutsa_week5_hw.dto.AddressUpdateDto;
import mutsa.mutsa_week5_hw.repository.AddressRepository;
import mutsa.mutsa_week5_hw.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {

    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;


    // 배송지 등록
    @Transactional
    public AddressResponseDto createAddress(Long memberId,
                                            AddressRequestDto dto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new IllegalArgumentException("회원이 존재하지 않습니다.")
                );

        Address address = Address.builder()
                .name(dto.getName())
                .zipcode(dto.getZipcode())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .member(member)
                .build();

        Address savedAddress = addressRepository.save(address);

        return AddressResponseDto.from(savedAddress);
    }


    // 배송지 전체 조회
    public List<AddressResponseDto> getAddresses(Long memberId) {

        return addressRepository.findByMemberId(memberId)
                .stream()
                .map(AddressResponseDto::from)
                .toList();
    }


    // 배송지 수정
    @Transactional
    public AddressResponseDto updateAddress(Long addressId,
                                            AddressUpdateDto dto) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new IllegalArgumentException("배송지가 존재하지 않습니다.")
                );

        address.update(
                dto.getName(),
                dto.getZipcode(),
                dto.getAddress(),
                dto.getPhone()
        );

        return AddressResponseDto.from(address);
    }


    // 배송지 삭제
    @Transactional
    public void deleteAddress(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new IllegalArgumentException("배송지가 존재하지 않습니다.")
                );

        addressRepository.delete(address);
    }
}
