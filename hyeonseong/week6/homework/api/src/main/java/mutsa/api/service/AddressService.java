package mutsa.api.service;

import lombok.RequiredArgsConstructor;
import mutsa.api.domain.Address;
import mutsa.api.domain.User;
import mutsa.api.dto.AddressRequestDto;
import mutsa.api.dto.AddressResponseDto;
import mutsa.api.global.apiPayload.code.AddressErrorCode;
import mutsa.api.global.apiPayload.code.GeneralErrorCode;
import mutsa.api.global.apiPayload.code.UserErrorCode;
import mutsa.api.global.apiPayload.exception.ProjectException;
import mutsa.api.repository.AddressRepository;
import mutsa.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    // [생성] 새로운 배송지 등록
    @Transactional
    public void createAddress(Long userId, AddressRequestDto requestDto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        Address address = Address.builder()
                .user(user)
                .addressName(requestDto.getAddressName())
                .zipcode(requestDto.getZipcode())
                .address(requestDto.getAddress())
                .phoneNumber(requestDto.getPhoneNumber())
                .build();

        addressRepository.save(address);
    }

    // [조회] 내 배송지 목록 전체 조회
    public List<AddressResponseDto> getAllAddresses(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));
        return addressRepository.findAllByUser(user).stream()
                .map(AddressResponseDto::of)
                .toList();
    }

    // [수정] 기존 배송지 정보 수정
    @Transactional
    public void updateAddress(Long addressId, Long userId, AddressRequestDto requestDto){
        Address address = addressRepository.findById(addressId)
                .orElseThrow(()-> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));

        if (!address.getUser().getId().equals(userId)){
            throw new ProjectException(GeneralErrorCode.FORBIDDEN);
        }

        address.updateAddress(
                requestDto.getAddressName(),
                requestDto.getZipcode(),
                requestDto.getAddress(),
                requestDto.getPhoneNumber()
        );
    }

    // [삭제] 특정 배송지 삭제
    @Transactional
    public void deleteAddress(Long addressId, Long userId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));

        if (!address.getUser().getId().equals(userId)){
            throw new ProjectException(GeneralErrorCode.FORBIDDEN);
        }

        addressRepository.delete(address);
    }
}