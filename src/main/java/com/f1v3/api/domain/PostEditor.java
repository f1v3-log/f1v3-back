package com.f1v3.api.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * 게시글에 수정을 할 수 있는 요소들만 담은 클래스.
 */
@Getter
public class PostEditor {

    private final String title;
    private final String content;

    @Builder
    public PostEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
