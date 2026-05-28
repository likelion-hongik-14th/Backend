package mutsa.w5homework.service;

import lombok.RequiredArgsConstructor;
import mutsa.w5homework.domain.Address;
import mutsa.w5homework.domain.Member;
import mutsa.w5homework.dto.AddressDto;
import mutsa.w5homework.global.apiPayload.code.status.AddressErrorCode;
import mutsa.w5homework.global.exception.GeneralException;
import mutsa.w5homework.repository.AddressRepository;
import mutsa.w5homework.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//필수 생성자 자동 생성 -> DI 주입
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    //배송지 신규 등록
    @Transactional
    public AddressDto.Response createAddress(AddressDto.CreateRequest dto){
        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow(()
                -> new RuntimeException("Member not found"));
        Address address = Address.builder()
                .addressName(dto.getAddressName())
                .zipCode(dto.getZipCode())
                .cityAddress(dto.getCityAddress())
                .phoneNumber(dto.getPhoneNumber())
                .member(member)
                .build();
        Address savedAddress = addressRepository.save(address);
        return new AddressDto.Response(savedAddress);
    }

    //배송지 전체 조회
    @Transactional(readOnly = true)
    public List<AddressDto.Response> getAllAddress(Long memberId){
        //memberId와 동일한 모든 행 조회 및 address 객체로 변환
        List<Address> addresses = addressRepository.findByMemberId(memberId);
        return addresses.stream().map(AddressDto.Response::new).toList();
    }

    //배송지 정보 수정
    @Transactional
    public AddressDto.Response updateAddress(Long addressId, AddressDto.UpdateRequest dto){
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new GeneralException(AddressErrorCode.ADDRESS_NOT_FOUND));
        address.update(dto.getAddressName(), dto.getZipCode(), dto.getCityAddress(), dto.getPhoneNumber());
        return new AddressDto.Response(address);
    }

    //배송지 삭제
    @Transactional
    public AddressDto.Response deleteAddress(Long addressId){
        Address address = addressRepository.findById(addressId)
                .orElseThrow(()-> new GeneralException(AddressErrorCode.ADDRESS_NOT_FOUND));
        addressRepository.delete(address);
        return new AddressDto.Response(address);
    }
}
