package com.f1v3.api.controller;

import com.f1v3.api.config.AppConfig;
import com.f1v3.api.request.Login;
import com.f1v3.api.response.SessionResponse;
import com.f1v3.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppConfig appConfig;
    private final AuthService authService;

    /**
     * JWT 사용하여 로그인 처리
     * @param login 로그인 정보
     * @return status: 200 (OK)
     */
    @PostMapping("/login")
    public SessionResponse login(@RequestBody Login login) {
        Long userId = authService.signIn(login);

        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        String jws = Jwts.builder()
                .subject(String.valueOf(userId))
                .signWith(key)
                .issuedAt(new Date())
                .compact();

        return new SessionResponse(jws);
    }

}
