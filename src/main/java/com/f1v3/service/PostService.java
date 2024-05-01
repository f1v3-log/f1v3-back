package com.f1v3.service;

import com.f1v3.domain.Post;
import com.f1v3.dto.request.PostCreateRequest;
import com.f1v3.dto.response.PostCreateResponse;
import com.f1v3.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    /**
     * 게시글 작성 메서드입니다.
     *
     * @param postCreate 게시글 제목 및 내용을 담은 DTO
     */
    public PostCreateResponse write(PostCreateRequest postCreate) {

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);

        return new PostCreateResponse(post.getId());
    }
}
