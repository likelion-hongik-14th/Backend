package mutsa.api.service;

import lombok.RequiredArgsConstructor;
import mutsa.api.domain.Address;
import mutsa.api.domain.User;
import mutsa.api.dto.AddressRequestDto;
import mutsa.api.dto.AddressResponseDto;
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

    // [공통] 테스트용 임시 유저 조회 및 생성
    private User getTestUser() {
        return userRepository.findById(1L).orElseGet(() -> {
            User newUser = User.builder()
                    .email("test@mutsa.com")
                    .password("1234")
                    .name("테스트유저")
                    .build();
            return userRepository.save(newUser);
        });
    }

    // [생성] 새로운 배송지 등록
    @Transactional
    public void createAddress(AddressRequestDto requestDto){
        User user = getTestUser();

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
    public List<AddressResponseDto> getAllAddresses(){
        User user = getTestUser();
        return addressRepository.findAllByUser(user).stream()
                .map(AddressResponseDto::of)
                .toList();
    }

    // [수정] 기존 배송지 정보 수정
    @Transactional
    public void updateAddress(Long addressId, AddressRequestDto requestDto){
        Address address = addressRepository.findById(addressId)
                .orElseThrow(()->new IllegalArgumentException("수정할 배송지를 찾을 수 없습니다."));

        address.updateAddress(
                requestDto.getAddressName(),
                requestDto.getZipcode(),
                requestDto.getAddress(),
                requestDto.getPhoneNumber()
        );
    }

    // [삭제] 특정 배송지 삭제
    @Transactional
    public void deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 배송지를 찾을 수 없습니다."));

        addressRepository.delete(address);
    }
}