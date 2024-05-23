package com.f1v3.api.exception;

public class UserNotFound extends GeneralException {

    private static final String MESSAGE = "존재하지 않는 유저입니다.";

    public UserNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
