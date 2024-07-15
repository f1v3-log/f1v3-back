package com.f1v3.api.controller;


import com.f1v3.api.config.UserPrincipal;
import com.f1v3.api.request.Signup;
import com.f1v3.api.response.UserResponse;
import com.f1v3.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 컨트롤러 클래스.
 */

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * User 정보를 가져오는 메서드.
     */
    @GetMapping("/users/me")
    public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        if (userPrincipal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(userService.getUserProfile(userPrincipal.getUserId()));
    }

    /**
     * 회원가입 처리 메서드.
     *
     * @param signup 회원 가입 정보
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/signup")
    public void signup(@RequestBody Signup signup) {
        userService.signup(signup);
    }
}
