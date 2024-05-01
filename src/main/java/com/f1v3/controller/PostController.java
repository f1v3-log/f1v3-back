package com.f1v3.controller;

import com.f1v3.request.PostCreateRequest;
import com.f1v3.response.PostCreateResponse;
import com.f1v3.response.PostResponse;
import com.f1v3.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시글 생성 메서드.
     *
     * @return status : 201(CREATED), body : 생성된 게시글 번호
     */
    @PostMapping("/posts")
    public ResponseEntity<PostCreateResponse> createPost(@RequestBody @Valid PostCreateRequest request) {
        PostCreateResponse response = postService.write(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse getPost(@PathVariable("postId") Long id) {
        return postService.get(id);
    }

}
