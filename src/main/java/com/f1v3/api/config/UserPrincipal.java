package com.f1v3.api.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrincipal extends User {

    private final Long userId;

    public UserPrincipal(com.f1v3.api.domain.User user) {
        super(user.getEmail(), user.getPassword(), List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("WRITE")));
        this.userId = user.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
