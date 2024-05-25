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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts/{postId}/comments")
    public void write(@PathVariable Long postId,
                      @RequestBody @Valid CommentCreate request) {

        commentService.write(postId, request);
    }

    @PostMapping("/comments/{commentId}/delete")
    public void delete(@PathVariable Long commentId,
                       @RequestBody @Valid CommentDelete request) {
        commentService.delete(commentId, request);
    }
}
