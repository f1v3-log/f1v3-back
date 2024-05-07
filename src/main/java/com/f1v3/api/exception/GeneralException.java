package com.f1v3.api.exception;

/**
 * 모든 예외의 상위 클래스.
 */
public abstract class GeneralException extends RuntimeException {

    protected GeneralException(String message) {
        super(message);
    }

    protected GeneralException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();
}
