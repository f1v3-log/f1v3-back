package com.f1v3.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * 회원가입 요청 클래스
 *
 * TODO: @Data -> 변환!!
 */

@Data
@Builder
public class Signup {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
}
