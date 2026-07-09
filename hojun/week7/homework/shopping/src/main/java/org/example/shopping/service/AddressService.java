package org.example.shopping.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.domain.Address;
import org.example.shopping.domain.User;
import org.example.shopping.dto.address.AddressRequestDto;
import org.example.shopping.dto.address.AddressResponseDto;
import org.example.shopping.global.apiPayload.code.domain.AddressErrorCode;
import org.example.shopping.global.apiPayload.code.domain.UserErrorCode;
import org.example.shopping.global.apiPayload.exception.ProjectException;
import org.example.shopping.repository.AddressRepository;
import org.example.shopping.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createAddress(Long userId, AddressRequestDto request) {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ProjectException(UserErrorCode.USER_NOT_FOUND));
        Address address = Address.builder()
                .user(user)
                .address(request.getAddress())
                .name(request.getName())
                .zipcode(request.getZipcode())
                .phone(request.getPhone())
                .build();

        user.getAddresses().add(address);
        addressRepository.save(address);
        return address.getId();
    }

    public List<AddressResponseDto> getAddressList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        List<Address> addresses = user.getAddresses();

        return addresses.stream()
                .map(address -> new AddressResponseDto(
                        address.getAddress(),
                        address.getName(),
                        address.getPhone(),
                        address.getZipcode()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateAddress(Long userId,Long addressId, AddressRequestDto request) {
        Address address = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(()-> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));
        address.updateInfo(
                request.getAddress(),
                request.getName(),
                request.getPhone(),
                request.getZipcode());
    }

    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        Address address = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(()-> new ProjectException(AddressErrorCode.ADDRESS_NOT_FOUND));
        addressRepository.delete(address);
    }
}

