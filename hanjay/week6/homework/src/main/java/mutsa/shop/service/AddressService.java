package mutsa.shop.service;

import lombok.RequiredArgsConstructor;
import mutsa.shop.domain.Address;
import mutsa.shop.domain.Member;
import mutsa.shop.dto.addressDto.AddressRequestDto;
import mutsa.shop.dto.addressDto.AddressResponseDto;
import mutsa.shop.repository.AddressRepository;
import mutsa.shop.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    public AddressResponseDto addAddress(AddressRequestDto addressRequestDto, Long memberId) {
        //배송지 추가
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Address address = Address.builder()
                .member(member)
                .name(addressRequestDto.getName())
                .postalcode(addressRequestDto.getPostalcode())
                .address(addressRequestDto.getAddress())
                .phone(addressRequestDto.getPhone())
                .build();
        Address savedAddress = addressRepository.save(address);

        // Entity -> ResponseDto 변환 후 반환
        return AddressResponseDto.from(savedAddress);
    }
    //배송지 조회
    public List<AddressResponseDto> getAddresses(Long memberId) {
        // 회원의 배송지 목록을 조회하여 DTO 리스트로 변환
        return addressRepository.findByMemberId(memberId).stream()
                .map(AddressResponseDto::from)
                .collect(Collectors.toList());
    }

    //배송지 정보 수정
    @Transactional
    public AddressResponseDto updateAddress(Long addressId, AddressRequestDto requestDto) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 배송지입니다. ID: " + addressId));

        // JPA 변경 감지(Dirty Checking)를 이용한 데이터 수정
        // 별도의 repository.save()를 호출하지 않아도 트랜잭션이 끝날 때 DB에 반영됩니다.
        address.update(
                requestDto.getName(),
                requestDto.getPostalcode(),
                requestDto.getAddress(),
                requestDto.getPhone()
        );

        return AddressResponseDto.from(address);
    }

    //배송지 삭제
    @Transactional
    public void deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 배송지입니다. ID: " + addressId));

        addressRepository.delete(address);
    }


}
