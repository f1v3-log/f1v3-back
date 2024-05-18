package com.f1v3.api.controller;

import com.f1v3.api.crypto.PasswordEncoder;
import com.f1v3.api.domain.Session;
import com.f1v3.api.domain.User;
import com.f1v3.api.repository.SessionRepository;
import com.f1v3.api.repository.UserRepository;
import com.f1v3.api.request.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    @BeforeEach
    void clear() {
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("로그인 성공")
    void login_Success() throws Exception {

        // given
        PasswordEncoder encoder = new PasswordEncoder();
        String encryptedPassword = encoder.encrypt("1234");

        userRepository.save(User.builder()
                .name("seungjo")
                .email("f1v3@kakao.com")
                .password(encryptedPassword)
                .build());

        Login login = Login.builder()
                .email("f1v3@kakao.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Disabled
    @Transactional
    @DisplayName("로그인 성공 후 세션 1개 생성")
    void login_Success_Create_Session() throws Exception {

        // given
        PasswordEncoder encoder = new PasswordEncoder();
        String encryptedPassword = encoder.encrypt("1234");

        User user = User.builder()
                .name("seungjo")
                .email("f1v3@kakao.com")
                .password(encryptedPassword)
                .build();

        userRepository.save(user);

        Login login = Login.builder()
                .email("f1v3@kakao.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        User loggedInUser = userRepository.findById(user.getId())
                .orElseThrow(RuntimeException::new);

        assertEquals(1L, loggedInUser.getSessions().size());
    }

    @Test
    @Transactional
    @DisplayName("로그인 성공 후 세션 응답")
    void login_Success_Response_Session() throws Exception {
        // given
        PasswordEncoder encoder = new PasswordEncoder();
        String encryptedPassword = encoder.encrypt("1234");

        User user = User.builder()
                .name("seungjo")
                .email("f1v3@kakao.com")
                .password(encryptedPassword)
                .build();

        userRepository.save(user);

        Login login = Login.builder()
                .email("f1v3@kakao.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andDo(print());
    }

    @Test
    @Disabled
    @DisplayName("로그인 후 권한이 필요한 페이지에 접속한다 /f1v3")
    void login_Success_Access_Page() throws Exception {

        // given
        PasswordEncoder encoder = new PasswordEncoder();
        String encryptedPassword = encoder.encrypt("1234");

        User user = User.builder()
                .name("seungjo")
                .email("f1v3@kakao.com")
                .password(encryptedPassword)
                .build();

        // 연관관계 설정
        Session session = user.addSession();

        userRepository.save(user);

        // expected
        mockMvc.perform(get("/f1v3")
                        .header("Authorization", session.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 후 검증되지 않은 세션 값인 경우 권한이 필요한 페이지에 접속 불가")
    void login_Not_Allow_Session() throws Exception {

        // given


        User user = User.builder()
                .name("seungjo")
                .email("f1v3@kakao.com")
                .password("1234")
                .build();

        // 연관관계 설정
        Session session = user.addSession();

        userRepository.save(user);

        String invalidSession = session.getAccessToken() + "-invalid";

        mockMvc.perform(get("/f1v3")
                        .header("Authorization", invalidSession)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
