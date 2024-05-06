package com.f1v3.api.exception;

/**
 * 게시글을 찾을 수 없을 때 발생하는 예외.
 */

public class PostNotFound extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 글입니다.";

    public PostNotFound() {
        super(MESSAGE);
    }
}
