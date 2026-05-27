package mutsa.homework.service;

import lombok.RequiredArgsConstructor;
import mutsa.homework.domain.Address;
import mutsa.homework.domain.User;
import mutsa.homework.dto.address.AddAddressRequestDto;
import mutsa.homework.dto.address.AddressResponseDto;
import mutsa.homework.dto.address.UpdateAddressRequestDto;
import mutsa.homework.global.dto.ListResponseDto;
import mutsa.homework.repository.AddressRepository;
import mutsa.homework.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Transactional
    public AddressResponseDto addAddress(Long userId, AddAddressRequestDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        if (addressRepository.existsByUser_IdAndAddressName(userId, requestDto.addressName())) {
            throw new IllegalArgumentException("주소지 '" + requestDto.addressName() + "'(이)가 이미 존재합니다.");
        }

        Address newAddress = Address.create(
                user,
                requestDto.addressName(),
                requestDto.address(),
                requestDto.zipCode(),
                requestDto.phoneNumber()
        );
        addressRepository.save(newAddress);

        return AddressResponseDto.from(newAddress);
    }

    public AddressResponseDto getAddress(Long userId, Long addressId){

        Address address = addressRepository.findById(addressId)
                .orElseThrow(()-> new IllegalArgumentException("잘못된 접근입니다. (주소지 조회 불가)"));

        address.validateUser(userId);

        return AddressResponseDto.from(address);
    }

    public ListResponseDto<AddressResponseDto> getAllAddress(Long userId){

        List<AddressResponseDto> addresses = addressRepository.findAllByUser_Id(userId).stream()
                .map(AddressResponseDto::from)
                .toList();

        return ListResponseDto.of(addresses);
    }

    @Transactional
    public AddressResponseDto updateAddress(Long userId, Long addressId, UpdateAddressRequestDto requestDto){

        Address address = addressRepository.findById(addressId)
                .orElseThrow(()-> new IllegalArgumentException("잘못된 접근입니다. (주소지 조회 불가)"));

        address.validateUser(userId);

        if (!address.getAddressName().equals(requestDto.addressName())){
            if (addressRepository.existsByUser_IdAndAddressName(userId, requestDto.addressName())){
                throw new IllegalArgumentException("이미 사용중인 배송지명입니다.");
            }
        }

        address.updateAddress(
                requestDto.addressName(),
                requestDto.address(),
                requestDto.zipCode(),
                requestDto.phoneNumber()
        );

        return AddressResponseDto.from(address);
    }

    @Transactional
    public void deleteAddress(Long userId, Long addressId){

        Address address = addressRepository.findById(addressId)
                .orElseThrow(()-> new IllegalArgumentException("잘못된 접근입니다. (주소지 조회 불가)"));

        address.validateUser(userId);

        addressRepository.delete(address);
    }

}
