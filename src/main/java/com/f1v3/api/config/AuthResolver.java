package com.f1v3.api.config;

import com.f1v3.api.config.data.UserSession;
import com.f1v3.api.exception.Unauthorized;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final AppConfig appConfig;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String jws = webRequest.getHeader("Authorization");
        if (Strings.isEmpty(jws)) {
            throw new Unauthorized();
        }

        // jws 복호화 작업 필요
        try {
            SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jws);

            log.info(">> claims = {}", claims);

            String userId = claims.getPayload().getSubject();

            // 세션이 만료된 경우 예외 처리
            Date expiration = claims.getPayload().getExpiration();
            if (expiration.before(new Date())) {
                throw new Unauthorized();
            }

            return new UserSession(Long.parseLong(userId));
        } catch (JwtException e) {
            throw new Unauthorized();
        }
    }
}
