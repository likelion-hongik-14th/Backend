package mutsa.api.service;

import lombok.RequiredArgsConstructor;
import mutsa.api.domain.User;
import mutsa.api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(String email, String password, String name) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User newUser = User.builder()
                .email(email)
                .password(encodedPassword)
                .name(name)
                .build();

        userRepository.save(newUser);
    }
}