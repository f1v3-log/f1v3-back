package com.f1v3.api.request;

import lombok.Builder;
import lombok.Data;

/**
 * 회원가입 요청 클래스
 *
 * TODO: @Data -> 변환!!
 */

@Data
public class Signup {
    private String account;
    private String email;
    private String password;
    private String name;

    @Builder
    public Signup(String account, String email, String password, String name) {
        this.account = account;
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
