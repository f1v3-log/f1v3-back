package com.f1v3.api.config;

import com.f1v3.api.config.filter.EmailPasswordAuthFilter;
import com.f1v3.api.config.handler.Http401Handler;
import com.f1v3.api.config.handler.Http403Handler;
import com.f1v3.api.config.handler.LoginFailHandler;
import com.f1v3.api.config.handler.LoginSuccessHandler;
import com.f1v3.api.domain.User;
import com.f1v3.api.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    /**
     * Security - ignore 설정 빈 등록.
     * error 페이지, static resource, h2-console
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/error")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers(PathRequest.toH2Console());
    }

    /**
     * Security 설정 빈 등록.
     * - CSRF 비활성화
     * - 모든 요청 허용
     * - UsernamePasswordAuthenticationFilter 앞에 EmailPasswordAuthFilter 추가
     * - 예외 처리 핸들러 등록 (접근 거부, 인증 실패)
     * - RememberMe 설정 (remember-me 쿠키 사용)
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> registry
                        .anyRequest().permitAll())
                .addFilterBefore(usernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> {
                    e.accessDeniedHandler(new Http403Handler(objectMapper));
                    e.authenticationEntryPoint(new Http401Handler(objectMapper));
                })
                .rememberMe(rm -> rm
                        .rememberMeParameter("remember")
                        .alwaysRemember(false)
                        .tokenValiditySeconds(86400 * 30))
                .build();
    }

    /**
     * EmailPasswordAuthFilter 빈 등록.
     * - /auth/login 경로로 요청이 올 경우, EmailPasswordAuthFilter 실행
     */
    @Bean
    public EmailPasswordAuthFilter usernamePasswordAuthenticationFilter() {
        EmailPasswordAuthFilter filter = new EmailPasswordAuthFilter("/auth/login", objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        filter.setAuthenticationFailureHandler(new LoginFailHandler(objectMapper));
        filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        filter.setRememberMeServices(rememberMeServices());

        return filter;
    }

    /**
     * RememberMeServices 빈 등록.
     * - SpringSessionRememberMeServices 사용
     * - remember-me 쿠키를 사용하여 로그인 유지
     */
    @Bean
    public RememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        rememberMeServices.setAlwaysRemember(true);
        rememberMeServices.setRememberMeParameterName("remember");
        rememberMeServices.setValiditySeconds(3600 * 24 * 30);

        return rememberMeServices;
    }

    /**
     * AuthenticationManager 빈 등록.
     * - DaoAuthenticationProvider 사용
     * - UserDetailsService 빈 주입 후 유저의 ID, Password 검증하는 역할
     * - PasswordEncoder를 통해 암호화된 Password 비교
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService(userRepository));
        provider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(provider);
    }

    /**
     * UserDetailsService 빈 등록.
     * - UserRepository 주입을 받아 UserPrincipal 반환
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));

            return new UserPrincipal(user);
        };
    }

    /**
     * 암호화된 Password를 비교하는 PasswordEncoder 빈 등록.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder(
                16,
                8,
                1,
                32,
                64);
    }
}
