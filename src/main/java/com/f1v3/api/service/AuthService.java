package com.f1v3.api.service;

import com.f1v3.api.domain.Session;
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
    public String signIn(Login login) {
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSignInInformation::new);

        Session session = user.addSession();
        return session.getAccessToken();
    }
}
