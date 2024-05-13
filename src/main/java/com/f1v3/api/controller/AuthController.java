package com.f1v3.api.controller;

import com.f1v3.api.domain.User;
import com.f1v3.api.request.Login;
import com.f1v3.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/auth/login")
    public User login(@RequestBody Login login) {
        // json -> id/pwd
        log.info(">> login = {}", login);

        // DB 조회 후 User 객체 반환
        return authService.signin(login);
    }
}
