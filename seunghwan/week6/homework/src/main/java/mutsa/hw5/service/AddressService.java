package mutsa.hw5.service;

import lombok.RequiredArgsConstructor;
import mutsa.hw5.domain.Address;
import mutsa.hw5.domain.Member;
import mutsa.hw5.dto.address.AddressRequestDto;
import mutsa.hw5.dto.address.AddressResponseDto;
import mutsa.hw5.dto.address.AddressUpdateDto;
import mutsa.hw5.repository.AddressRepository;
import mutsa.hw5.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service // JPA에게 이 클래스가 서비스 클래스라는 걸 명시

// final 필드들의 생성자를 자동 생성
// Spring이 이 생성자를 보고 Repository들을 자동으로 주입해줌 (의존성 주입)
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    // 배송지 등록
    @Transactional // SQLD에서 나오는 ACID 중에 그 원자성을 의미
    public AddressResponseDto createAddress(AddressRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        Address address = addressRepository.save(dto.toEntity(member));
        return AddressResponseDto.from(address);
    }

    // 배송지 목록 조회
    @Transactional(readOnly = true) // "readOnly = true"의 의미: DB를 조회만 하고 변경은 안 한다는 뜻
    public List<AddressResponseDto> getAddresses(Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        return addressRepository.findAllByMember_MemberId(memberId).stream()
                .map(AddressResponseDto::from)
                .collect(Collectors.toList());
    }

    // 배송지 수정
    @Transactional
    public AddressResponseDto updateAddress(Long addressId, Long memberId, AddressUpdateDto dto) {
        Address address = addressRepository.findByAddressIdAndMember_MemberId(addressId, memberId)
                .orElseThrow(() -> new RuntimeException("배송지를 찾을 수 없습니다."));
        address.update(dto.getReceiverName(), dto.getAddressName(), dto.getPostalCode(), dto.getAddress(), dto.getPhoneNumber());
        return AddressResponseDto.from(address);
    }

    // 배송지 삭제
    @Transactional
    public void deleteAddress(Long addressId, Long memberId) {
        Address address = addressRepository.findByAddressIdAndMember_MemberId(addressId, memberId)
                .orElseThrow(() -> new RuntimeException("배송지를 찾을 수 없습니다."));
        addressRepository.delete(address);
    }
}
