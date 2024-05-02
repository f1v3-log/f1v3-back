package com.f1v3.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 게시글 생성 요청 클래스.
 */

@Getter
@Setter
@ToString
public class PostCreateRequest {

    @NotBlank(message = "title은 필수 값입니다.")
    private final String title;

    @NotBlank(message = "content는 필수 값입니다.")
    private final String content;


    @Builder
    public PostCreateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostCreateRequest changeTitle(String title) {
        return PostCreateRequest.builder()
                .title(title)
                .content(this.content)
                .build();
    }


}