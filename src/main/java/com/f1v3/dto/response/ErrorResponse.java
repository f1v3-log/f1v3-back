package com.f1v3.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 에러 응답 DTO.
 *
 * <p>
 * {
 * "code": "400",
 * "message": "잘못된 요청입니다.",
 * "validation": {
 * "title": "값을 입력해주세요"
 * }
 * }
 * </p>
 */

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;
    private final List<ValidationTuple> validation = new ArrayList<>();

    public void addValidation(String field, String errorMessage) {
        validation.add(new ValidationTuple(field, errorMessage));
    }

    @Getter
    @RequiredArgsConstructor
    private static class ValidationTuple {
        private final String fieldName;
        private final String errorMessage;
    }
}
