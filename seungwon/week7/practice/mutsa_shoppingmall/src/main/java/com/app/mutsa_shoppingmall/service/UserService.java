package com.app.mutsa_shoppingmall.service;

import com.app.mutsa_shoppingmall.dto.UserDto;
import com.app.mutsa_shoppingmall.entity.User;
import com.app.mutsa_shoppingmall.exception.ProjectException;
import com.app.mutsa_shoppingmall.exception.code.UserErrorCode;
import com.app.mutsa_shoppingmall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    // 1. 회원가입
    @Transactional
    public UserDto.Response register(UserDto.RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ProjectException(UserErrorCode.USER_ALREADY_EXISTS);
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        return UserDto.Response.from(userRepository.save(user));
    }

    // 2. 단건 조회
    public UserDto.Response getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        return UserDto.Response.from(user);
    }

    // 3. 정보 수정
    @Transactional
    public UserDto.Response updateUser(Long userId, UserDto.UpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(UserErrorCode.USER_NOT_FOUND));

        user.update(request.getUsername(), request.getEmail());

        return UserDto.Response.from(user);
    }

    // 4. 회원 탈퇴
    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ProjectException(UserErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(userId);
    }
}