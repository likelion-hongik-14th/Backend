package org.example.shopping.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.domain.Order;
import org.example.shopping.domain.User;
import org.example.shopping.dto.user.UserRequestDto;
import org.example.shopping.dto.user.UserResponseDto;
import org.example.shopping.global.apiPayload.code.domain.UserErrorCode;
import org.example.shopping.global.apiPayload.exception.ProjectException;
import org.example.shopping.repository.OrderRepository;
import org.example.shopping.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long signup(UserRequestDto request){
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        userRepository.save(user);
        return user.getId();
    }

    public List<UserResponseDto> getUsers(){
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDto(user.getName(), user.getEmail()))
                .toList();
    }

    @Transactional
    public void updateUser(Long userId, UserRequestDto request){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ProjectException(UserErrorCode.USER_NOT_FOUND));
        user.updateInfo(request.getName(), request.getEmail(), request.getPassword());
    }

    @Transactional
    public void deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        List<Order> orders = user.getOrders();
        for(Order order : orders){
            order.disconnectUser();
        }
        userRepository.deleteById(userId);
    }

}
