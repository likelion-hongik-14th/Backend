package mutsa.session5.Service;

import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.AddressRequestDto;
import mutsa.session5.Dto.AddressResponseDto;
import mutsa.session5.Entity.Address;
import mutsa.session5.Entity.Member;
import mutsa.session5.Repository.AddressRepository;
import mutsa.session5.Repository.MemberRepository;
import mutsa.session5.global.apipayload.exception.AddressException;
import mutsa.session5.global.apipayload.exception.MemberException;
import mutsa.session5.global.apipayload.exception.code.AddressErrorCode;
import mutsa.session5.global.apipayload.exception.code.MemberErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    // 다중 배송지 등록
    public AddressResponseDto addAddress(AddressRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Address address = Address.builder()
                .member(member)
                .addressName(requestDto.getAddressName())
                .zipCode(requestDto.getZipCode())
                .baseAddress(requestDto.getBaseAddress())
                .detailAddress(requestDto.getDetailAddress())
                .phoneNumber(requestDto.getPhoneNumber())
                .build();

        Address savedAddress = addressRepository.save(address);

        return AddressResponseDto.from(savedAddress);
    }

    // 전체 목록 조회
    @Transactional(readOnly = true)
    public List<AddressResponseDto> getAddressList(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return addressRepository.findAllByMember(member).stream()
                .map(AddressResponseDto::from)
                .toList();
    }

    // 기존 배송지 정보 수정
    public AddressResponseDto updateAddress(Long addressId, AddressRequestDto requestDto) {
        Address address = addressRepository
                .findByAddressIdAndMember_MemberId(addressId, requestDto.getMemberId())
                .orElseThrow(() -> new AddressException(AddressErrorCode.ADDRESS_NOT_FOUND));

        address.updateAddress(
                requestDto.getAddressName(),
                requestDto.getZipCode(),
                requestDto.getBaseAddress(),
                requestDto.getDetailAddress(),
                requestDto.getPhoneNumber()
        );

        return AddressResponseDto.from(address);
    }
}
