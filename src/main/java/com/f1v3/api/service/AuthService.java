package com.f1v3.api.service;

import com.f1v3.api.domain.User;
import com.f1v3.api.exception.InvalidSigninInformation;
import com.f1v3.api.repository.UserRepository;
import com.f1v3.api.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User signin(Login login) {
        return userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);
    }
}
