package com.f1v3.api.controller;

import com.f1v3.api.config.UserPrincipal;
import com.f1v3.api.request.post.PostCreate;
import com.f1v3.api.request.post.PostEdit;
import com.f1v3.api.request.post.PostSearch;
import com.f1v3.api.response.PostCreateResponse;
import com.f1v3.api.response.PostResponse;
import com.f1v3.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public PostCreateResponse createPost(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                         @RequestBody @Valid PostCreate request) {
        request.validate();
        return postService.write(userPrincipal.getUserId(), request);
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
     * 게시글 다중 조회 (Pagination) 메서드.
     *
     * @param postSearch 게시글 검색 조건
     * @return status : 200 (OK), body: 게시글 응답 리스트
     */
    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    /**
     * 게시글 수정 메서드.
     *
     * @param postId  게시글 ID
     * @param request 게시글 수정 내용 객체
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }

    /**
     * 게시글 삭제 메서드.
     *
     * @param postId 게시글 ID
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') && hasPermission(#postId, 'POST', 'DELETE')")
    @DeleteMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}
