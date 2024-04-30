package com.f1v3.controller;

import com.f1v3.dto.request.PostCreateRequest;
import com.f1v3.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시글 생성 메서드.
     *
     * @return status : 200, body : "Hello World"
     */
    @PostMapping("/posts")
    public Map<String, String> createPost(@RequestBody @Valid PostCreateRequest request) {

        postService.write(request);
        return Map.of();
    }
}
