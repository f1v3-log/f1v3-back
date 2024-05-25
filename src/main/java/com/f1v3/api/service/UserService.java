package com.f1v3.api.service;

import com.f1v3.api.domain.User;
import com.f1v3.api.exception.AlreadyExistsEmailException;
import com.f1v3.api.repository.user.UserRepository;
import com.f1v3.api.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void signup(Signup signup) {

        if (userRepository.existsByEmail(signup.getEmail())) {
            throw new AlreadyExistsEmailException();
        }

        // 암호화 진행
        String encryptedPassword = passwordEncoder.encode(signup.getPassword());

        User user = User.builder()
                .email(signup.getEmail())
                .name(signup.getName())
                .password(encryptedPassword)
                .build();

        userRepository.save(user);
    }
}
