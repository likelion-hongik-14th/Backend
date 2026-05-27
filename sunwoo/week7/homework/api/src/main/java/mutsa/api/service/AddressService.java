package mutsa.api.service;

import lombok.RequiredArgsConstructor;
import mutsa.api.domain.Address;
import mutsa.api.domain.Users;
import mutsa.api.dto.AddressRequestDto;
import mutsa.api.dto.AddressResponseDto;
import mutsa.api.global.apiPayload.code.GeneralErrorCode;
import mutsa.api.global.apiPayload.exception.ProjectException;
import mutsa.api.repository.AddressRepository;
import mutsa.api.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public AddressResponseDto createAddress(Long userId, AddressRequestDto requestDto) {
        Users users = getUsers(userId);
        Address address = Address.create(
                users,
                requestDto.getName(),
                requestDto.getZipCode(),
                requestDto.getAddress(),
                requestDto.getPhoneNumber()
        );

        return AddressResponseDto.from(addressRepository.save(address));
    }

    @Transactional(readOnly = true)
    public List<AddressResponseDto> getAddresses(Long userId) {
        validateUserExists(userId);
        return addressRepository.findByUsersId(userId).stream()
                .map(AddressResponseDto::from)
                .toList();
    }

    @Transactional
    public AddressResponseDto updateAddress(Long userId, Long addressId, AddressRequestDto requestDto) {
        validateUserExists(userId);
        Address address = getAddress(userId, addressId);
        address.update(
                requestDto.getName(),
                requestDto.getZipCode(),
                requestDto.getAddress(),
                requestDto.getPhoneNumber()
        );

        return AddressResponseDto.from(address);
    }

    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        validateUserExists(userId);
        Address address = getAddress(userId, addressId);
        addressRepository.delete(address);
    }

    private void validateUserExists(Long userId) {
        if (!usersRepository.existsById(userId)) {
            throw new ProjectException(GeneralErrorCode.USER_NOT_FOUND);
        }
    }

    private Users getUsers(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.USER_NOT_FOUND));
    }

    private Address getAddress(Long userId, Long addressId) {
        return addressRepository.findByIdAndUsersId(addressId, userId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.ADDRESS_NOT_FOUND));
    }
}
