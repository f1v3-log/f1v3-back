package com.f1v3.api.request.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CommentCreate {

    @NotBlank(message = "작성자를 입력해주세요.")
    @Length(min = 1, max = 8, message = "작성자는 1자 이상 8자 이하로 입력해주세요.")
    private String author;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 4, max = 30, message = "비밀번호는 5자 이상 30자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "내용을 입력해주세요.")
    @Length(min = 10, max = 1000, message = "내용은 10자 이상 1000자 이하로 입력해주세요.")
    private String content;

    @Builder
    public CommentCreate(String author, String password, String content) {
        this.author = author;
        this.password = password;
        this.content = content;
    }
}
