package com.f1v3.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 게시글 생성 요청 DTO
 */

@ToString
@Getter
@Setter
public class PostCreateRequest {

    @NotBlank(message = "title은 필수 값입니다.")
    private String title;

    @NotBlank(message = "content는 필수 값입니다.")
    private String content;
}
