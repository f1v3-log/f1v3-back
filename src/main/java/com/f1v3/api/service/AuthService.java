package com.f1v3.api.service;

import com.f1v3.api.crypto.PasswordEncoder;
import com.f1v3.api.domain.User;
import com.f1v3.api.exception.InvalidSignInInformation;
import com.f1v3.api.repository.UserRepository;
import com.f1v3.api.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signIn(Login login) {

        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(InvalidSignInInformation::new);

        PasswordEncoder encoder = new PasswordEncoder();
        boolean isMatch = encoder.matches(login.getPassword(), user.getPassword());

        if (isMatch) {
            return user.getId();
        }

        throw new

                InvalidSignInInformation();
    }
}
