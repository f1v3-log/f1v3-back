package com.f1v3.controller;

import com.f1v3.request.PostCreateRequest;
import com.f1v3.response.PostCreateResponse;
import com.f1v3.response.PostResponse;
import com.f1v3.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ResponseStatus(HttpStatus.CREATED)
    public PostCreateResponse createPost(@RequestBody @Valid PostCreateRequest request) {
        return postService.write(request);
    }

    /**
     * 게시글 단건 조회 메서드.
     *
     * @param postId 게시글 ID
     * @return status : 200 (OK), body: 게시글 응답
     */
    @GetMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
    }

    /**
     * 게시글 다중 조회 메서드.
     */
    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getList() {
        return postService.getList();
    }
}
