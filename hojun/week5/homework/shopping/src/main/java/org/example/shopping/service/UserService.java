package org.example.shopping.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.domain.User;
import org.example.shopping.dto.UserRequestDto;
import org.example.shopping.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void signup(UserRequestDto request){
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        userRepository.save(user);
    }
}
