package mutsa.homework.service;

import lombok.RequiredArgsConstructor;
import mutsa.homework.domain.Address;
import mutsa.homework.domain.User;
import mutsa.homework.dto.address.AddAddressRequestDto;
import mutsa.homework.dto.address.AddressResponseDto;
import mutsa.homework.dto.address.UpdateAddressRequestDto;
import mutsa.homework.global.apiPayload.code.AddressErrorCode;
import mutsa.homework.global.apiPayload.code.UserErrorCode;
import mutsa.homework.global.apiPayload.exception.ProjectException;
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
                .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        if (addressRepository.existsByUser_IdAndAddressName(userId, requestDto.addressName())) {
            throw new ProjectException(AddressErrorCode.ADDRESS_ALREADY_EXISTS);
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
                .orElseThrow(()-> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));

        address.validateUser(userId);

        return AddressResponseDto.from(address);
    }

    public ListResponseDto<AddressResponseDto> getAllAddress(Long userId){

        userRepository.findById(userId)
                .orElseThrow(()-> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        List<AddressResponseDto> addresses = addressRepository.findAllByUser_Id(userId).stream()
                .map(AddressResponseDto::from)
                .toList();

        return ListResponseDto.of(addresses);
    }

    @Transactional
    public AddressResponseDto updateAddress(Long userId, Long addressId, UpdateAddressRequestDto requestDto){

        Address address = addressRepository.findById(addressId)
                .orElseThrow(()-> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));

        address.validateUser(userId);

        if (!address.getAddressName().equals(requestDto.addressName())){
            if (addressRepository.existsByUser_IdAndAddressName(userId, requestDto.addressName())){
                throw new ProjectException(AddressErrorCode.ADDRESS_NAME_ALREADY_EXISTS);
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
                .orElseThrow(()-> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));

        address.validateUser(userId);

        addressRepository.delete(address);
    }

}
