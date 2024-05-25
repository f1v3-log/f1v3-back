package com.f1v3.api.exception;

public class CommentNotFound extends GeneralException {

    private static final String MESSAGE = "존재하지 않는 댓글입니다.";

    public CommentNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
