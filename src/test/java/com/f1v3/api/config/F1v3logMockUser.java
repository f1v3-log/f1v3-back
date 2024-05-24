package com.f1v3.api.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = F1v3logMockSecurityContext.class)
public @interface F1v3logMockUser {

    String name() default "seungjo";

    String email() default "f1v3@kakao.com";

    String password() default "";
}
