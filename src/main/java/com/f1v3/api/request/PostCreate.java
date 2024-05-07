package com.f1v3.api.request;

import com.f1v3.api.exception.InvalidRequest;
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
public class PostCreate {

    @NotBlank(message = "title은 필수 값입니다.")
    private final String title;

    @NotBlank(message = "content는 필수 값입니다.")
    private final String content;


    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validate() {
        if (title.contains("광고")) {
            throw new InvalidRequest("title", "제목에 광고를 포함할 수 없습니다.");
        }
    }
}
