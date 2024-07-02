package com.f1v3.api.config;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * 사용자 정보를 담는 클래스.
 * (Spring Security - User 클래스의 확장 클래스)
 */
@Getter
public class UserPrincipal extends User {

    private final Long userId;

    public UserPrincipal(com.f1v3.api.domain.User user) {
        super(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        this.userId = user.getId();
    }

}
