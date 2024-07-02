package com.f1v3.api.controller;

import com.f1v3.api.request.comment.CommentCreate;
import com.f1v3.api.request.comment.CommentDelete;
import com.f1v3.api.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성 메서드.
     * @param postId 게시글 ID
     * @param request 댓글 작성 요청 객체 (작성자, 비밀번호, 내용)
     *
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts/{postId}/comments")
    public void write(@PathVariable Long postId,
                      @RequestBody @Valid CommentCreate request) {

        commentService.write(postId, request);
    }

    /**
     * 댓글 삭제 메서드.
     * @param commentId 댓글 ID
     * @param request 댓글 삭제 요청 객체 (비밀번호)
     */
    @PostMapping("/comments/{commentId}/delete")
    public void delete(@PathVariable Long commentId,
                       @RequestBody @Valid CommentDelete request) {
        commentService.delete(commentId, request);
    }
}
