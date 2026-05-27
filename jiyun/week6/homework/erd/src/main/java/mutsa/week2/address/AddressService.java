package mutsa.week2.address;

import lombok.RequiredArgsConstructor;
import mutsa.week2.common.error.BusinessException;
import mutsa.week2.common.error.ErrorCode;
import mutsa.week2.member.Member;
import mutsa.week2.member.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {

    private final AddressRepository addressRepository;
    private final MemberService memberService;

    @Transactional
    public AddressResponseDto create(Long memberId, AddressCreateRequestDto requestDto) {
        Member member = memberService.getMember(memberId);
        Address address = Address.builder()
                .member(member)
                .name(requestDto.getName())
                .zipCode(requestDto.getZipCode())
                .address(requestDto.getAddress())
                .phone(requestDto.getPhone())
                .build();
        return AddressResponseDto.from(addressRepository.save(address));
    }

    public AddressListResponseDto findByMember(Long memberId) {
        memberService.getMember(memberId);
        return AddressListResponseDto.of(
                addressRepository.findByMember_Id(memberId).stream()
                        .map(AddressResponseDto::from)
                        .toList()
        );
    }

    @Transactional
    public AddressResponseDto update(Long memberId, Long addressId, AddressUpdateRequestDto requestDto) {
        Address address = getOwnedAddress(memberId, addressId);
        address.update(
                requestDto.getName(),
                requestDto.getZipCode(),
                requestDto.getAddress(),
                requestDto.getPhone()
        );
        return AddressResponseDto.from(address);
    }

    public Address getOwnedAddress(Long memberId, Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ADDRESS_NOT_FOUND));
        if (!address.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
        return address;
    }
}
