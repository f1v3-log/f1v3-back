package com.f1v3.api.response;

import com.f1v3.api.domain.Post;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 서비스 정책에 맞는 응답 클래스.
 */
@Getter
@ToString
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final LocalDate regDate;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.regDate = post.getRegDateTime().toLocalDate();
    }
}
