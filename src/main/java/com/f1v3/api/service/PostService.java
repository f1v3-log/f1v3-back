package com.f1v3.api.service;

import com.f1v3.api.domain.Post;
import com.f1v3.api.domain.PostEditor;
import com.f1v3.api.domain.User;
import com.f1v3.api.exception.PostNotFound;
import com.f1v3.api.exception.UserNotFound;
import com.f1v3.api.repository.PostRepository;
import com.f1v3.api.repository.UserRepository;
import com.f1v3.api.request.PostCreate;
import com.f1v3.api.request.PostEdit;
import com.f1v3.api.request.PostSearch;
import com.f1v3.api.response.PostCreateResponse;
import com.f1v3.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * 게시글 작성 메서드입니다.
     *
     * @param postCreate 게시글 제목 및 내용을 담은 DTO
     */
    @Transactional
    public PostCreateResponse write(Long userId, PostCreate postCreate) {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);


        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .user(user)
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
    @Transactional(readOnly = true)
    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

    }

    /**
     * 페이징 처리된 게시글 다중 조회 메서드입니다.
     */
    @Transactional(readOnly = true)
    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .toList();
    }

    /**
     * 게시글 수정
     *
     * @param id       게시글 ID
     * @param postEdit 게시글 수정 내용
     */
    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder
                .title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);
    }

    /**
     * 게시글 삭제
     *
     * @param id 게시글 ID
     */
    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }
}
