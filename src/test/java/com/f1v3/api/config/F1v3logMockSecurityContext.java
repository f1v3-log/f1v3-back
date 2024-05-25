package com.f1v3.api.config;


import com.f1v3.api.domain.User;
import com.f1v3.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

@RequiredArgsConstructor
public class F1v3logMockSecurityContext implements WithSecurityContextFactory<F1v3logMockUser> {

    private final UserRepository userRepository;

    @Override
    public SecurityContext createSecurityContext(F1v3logMockUser annotation) {

        var user = User.builder()
                .name(annotation.name())
                .email(annotation.email())
                .password(annotation.password())
                .build();

        userRepository.save(user);

        var principal = new UserPrincipal(user);
        var role = new SimpleGrantedAuthority("ROLE_ADMIN");
        var auth = new UsernamePasswordAuthenticationToken(principal,
                user.getPassword(),
                List.of(role));

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);

        return context;
    }
}
