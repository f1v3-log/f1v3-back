package com.f1v3.api.service;

import com.f1v3.api.crypto.ScryptPasswordEncoder;
import com.f1v3.api.domain.User;
import com.f1v3.api.exception.InvalidSignInInformation;
import com.f1v3.api.repository.UserRepository;
import com.f1v3.api.request.Login;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @BeforeEach
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void login_Success() {
        // given
        ScryptPasswordEncoder encoder = new ScryptPasswordEncoder();
        String encryptedPassword = encoder.encrypt("1234");

        User user = User.builder()
                .name("승조 분신")
                .email("f1v3@naver.com")
                .password(encryptedPassword)
                .build();

        userRepository.save(user);

        Login login = Login.builder()
                .email("f1v3@naver.com")
                .password("1234")
                .build();

        // when
        Long userId = authService.signIn(login);

        // then
        Assertions.assertNotNull(userId);
    }


    @Test
    @DisplayName("로그인 실패 - 비밀번호 틀린 경우")
    void login_Invalid() {
        // given
        ScryptPasswordEncoder encoder = new ScryptPasswordEncoder();
        String encryptedPassword = encoder.encrypt("1234");

        User user = User.builder()
                .name("승조 분신")
                .email("f1v3@naver.com")
                .password(encryptedPassword)
                .build();

        userRepository.save(user);

        Login login = Login.builder()
                .email("f1v3@naver.com")
                .password("4321")
                .build();

        // expected
        Assertions.assertThrows(InvalidSignInInformation.class, () -> authService.signIn(login));
    }
}