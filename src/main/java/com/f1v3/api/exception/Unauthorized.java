package com.f1v3.api.exception;

/**
 * 인증되지 않은 사용자가 접근할 때 발생하는 예외.
 */
public class Unauthorized extends GeneralException {

    private static final String MESSAGE = "인증이 필요합니다.";

    public Unauthorized() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
