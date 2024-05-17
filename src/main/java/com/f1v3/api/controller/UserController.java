package com.f1v3.api.controller;


import com.f1v3.api.request.Signup;
import com.f1v3.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 컨트롤러 클래스.
 */

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 처리 메서드.
     * @param signup 회원 가입 정보
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public void signup(@RequestBody Signup signup) {
        userService.signup(signup);
    }
}
