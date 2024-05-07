package com.f1v3.api.exception;

/**
 * 잘못된 요청을 보낼 경우 발생하는 예외 클래스.
 * status : 400
 */

public class InvalidRequest extends GeneralException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
