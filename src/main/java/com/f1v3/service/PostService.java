package com.f1v3.service;

import com.f1v3.domain.Post;
import com.f1v3.repository.PostRepository;
import com.f1v3.request.PostCreateRequest;
import com.f1v3.request.PostSearch;
import com.f1v3.response.PostCreateResponse;
import com.f1v3.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 게시글 단건 조회 메서드입니다.
     *
     * @param id 게시글 ID
     * @return 게시글
     */
    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

    }

    /**
     * 페이징 처리된 게시글 다중 조회 메서드입니다.
     */
    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .toList();
    }
}
