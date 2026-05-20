package mutsa.session5.Service;

import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.AddressRequestDto;
import mutsa.session5.Dto.AddressResponseDto;
import mutsa.session5.Entity.Address;
import mutsa.session5.Entity.Member;
import mutsa.session5.Repository.AddressRepository;
import mutsa.session5.Repository.MemberRepository;
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
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        Address address = Address.builder()
                .member(member)
                .addressName(requestDto.getAddressName())
                .zipCode(requestDto.getZipCode())
                .baseAddress(requestDto.getBaseAddress())
                .detailAddress(requestDto.getDetailAddress())
                .phoneNumber(requestDto.getPhoneNumber())
                .build();

        Address savedAddress = addressRepository.save(address);

        return AddressResponseDto.builder()
                .addressId(savedAddress.getAddressId())
                .addressName(savedAddress.getAddressName())
                .zipCode(savedAddress.getZipCode())
                .baseAddress(savedAddress.getBaseAddress())
                .detailAddress(savedAddress.getDetailAddress())
                .phoneNumber(savedAddress.getPhoneNumber())
                .build();
    }

    // 전체 목록 조회
    @Transactional(readOnly = true)
    public List<AddressResponseDto> getAddressList(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        return addressRepository.findAllByMember(member).stream()
                .map(address -> AddressResponseDto.builder()
                        .addressId(address.getAddressId())
                        .addressName(address.getAddressName())
                        .zipCode(address.getZipCode())
                        .baseAddress(address.getBaseAddress())
                        .detailAddress(address.getDetailAddress())
                        .phoneNumber(address.getPhoneNumber())
                        .build())
                .toList();
    }

    // 기존 배송지 정보 수정
    public AddressResponseDto updateAddress(Long addressId, AddressRequestDto requestDto) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("수정할 배송지 정보를 찾을 수 없습니다."));

        address.updateAddress(
                requestDto.getAddressName(),
                requestDto.getZipCode(),
                requestDto.getBaseAddress(),
                requestDto.getDetailAddress(),
                requestDto.getPhoneNumber()
        );

        return AddressResponseDto.builder()
                .addressId(address.getAddressId())
                .addressName(address.getAddressName())
                .zipCode(address.getZipCode())
                .baseAddress(address.getBaseAddress())
                .detailAddress(address.getDetailAddress())
                .phoneNumber(address.getPhoneNumber())
                .build();
    }
}
