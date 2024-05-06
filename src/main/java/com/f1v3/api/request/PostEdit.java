package com.f1v3.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시글 수정 요청 클래스.
 */
@Getter
@Setter
public class PostEdit {

    @NotBlank(message = "title은 필수 값입니다.")
    private final String title;

    @NotBlank(message = "content는 필수 값입니다.")
    private final String content;


    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
