package com.f1v3.api.config;

import com.f1v3.api.config.data.UserSession;
import com.f1v3.api.exception.Unauthorized;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class AuthResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = webRequest.getHeader("Authorization");
        if (Strings.isEmpty(accessToken)) {
            throw new Unauthorized();
        }

        // DB 사용자 확인 작업
        // ... Primary ID를 넣어줌

        return new UserSession(1L);
    }
}
