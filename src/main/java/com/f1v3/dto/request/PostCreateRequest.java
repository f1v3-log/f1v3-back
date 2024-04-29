package com.f1v3.dto.request;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * 게시글 생성 요청 DTO
 */

@ToString
@AllArgsConstructor
public class PostCreateRequest {

    private String title;
    private String content;
}
