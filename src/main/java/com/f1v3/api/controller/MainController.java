package com.f1v3.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String main() {
        return "This is Main Page!!";
    }

    @GetMapping("/user")
    public String user() {

        return "반갑습니다 사용자님! 👶🏻";
    }

    @GetMapping("/admin")
    public String admin() {
        return "안녕하세요 관리자씨. 👨🏻";
    }
}
