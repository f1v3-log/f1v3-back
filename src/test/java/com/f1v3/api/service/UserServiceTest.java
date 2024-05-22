package com.f1v3.api.service;

import com.f1v3.api.domain.User;
import com.f1v3.api.exception.AlreadyExistsEmailException;
import com.f1v3.api.repository.UserRepository;
import com.f1v3.api.request.Signup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class UserServiceTest {


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    void clear() {
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("회원가입 성공")
    void signup_Success() {
        // given
        Signup signup = Signup.builder()
                .name("승조짱")
                .email("f1v3@naver.com")
                .password("1234")
                .build();

        // when
        userService.signup(signup);

        // then
        assertEquals(1L, userRepository.count());
        User user = userRepository.findAll().iterator().next();
        assertAll(
                () -> assertEquals(signup.getName(), user.getName()),
                () -> assertEquals(signup.getEmail(), user.getEmail()),
                () -> assertNotNull(user.getPassword())
        );
    }

    @Test
    @DisplayName("회원가입 중복 이메일 테스트")
    void signup_Duplicate_Email() {

        User user = User.builder()
                .name("승조 분신")
                .email("f1v3@naver.com")
                .password("1324")
                .build();

        userRepository.save(user);

        // given
        Signup signup = Signup.builder()
                .name("승조짱")
                .email("f1v3@naver.com")
                .password("1234")
                .build();

        // expected
        assertThrows(AlreadyExistsEmailException.class, () -> userService.signup(signup));
    }
}