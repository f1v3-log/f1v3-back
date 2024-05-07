package com.f1v3.api.exception;

import lombok.Getter;

/**
 * 잘못된 요청을 보낼 경우 발생하는 예외 클래스.
 * status : 400
 */

@Getter
public class InvalidRequest extends GeneralException {

    private static final String MESSAGE = "잘못된 요청입니다.";


    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
