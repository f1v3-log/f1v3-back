package com.f1v3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {


    /**
     * 기본 메서드
     * @return status : 200, body : "Hello f1v3"
     */
    @GetMapping("/posts")
    public String get() {
        return "Hello f1v3";
    }
}
