package com.f1v3.api.controller;


import com.f1v3.api.request.Signup;
import com.f1v3.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 처리 메서드.
     * @param signup 회원 가입 정보
     */
    @PostMapping("/user/signup")
    public void signup(@RequestBody Signup signup) {
        userService.signup(signup);
    }
}
