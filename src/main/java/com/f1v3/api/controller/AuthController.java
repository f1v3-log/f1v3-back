package com.f1v3.api.controller;

import com.f1v3.api.request.Login;
import com.f1v3.api.response.SessionResponse;
import com.f1v3.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Base64;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private static final String KEY = "fcn6scvfFoTGXuHqED0YweeKeEusGu5at2y/Y8oAyFY=";

    /**
     * JWT 사용하여 로그인 처리
     * @param login 로그인 정보
     * @return status: 200 (OK)
     */
    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
        Long userId = authService.signIn(login);

        // TODO : JWT Util 클래스를 통해 Key값 받아오기
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(KEY));

        String jws = Jwts.builder()
                .subject(String.valueOf(userId))
                .signWith(key)
                .compact();

        return new SessionResponse(jws);
    }
}
