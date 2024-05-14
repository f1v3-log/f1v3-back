package com.f1v3.api.exception;

public class InvalidSignInInformation extends GeneralException {

    private static final String MESSAGE = "이메일 또는 비밀번호를 확인해주세요.";

    public InvalidSignInInformation() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
