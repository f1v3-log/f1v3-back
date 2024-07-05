package com.f1v3.api.service;

import com.f1v3.api.domain.Post;
import com.f1v3.api.domain.PostEditor;
import com.f1v3.api.domain.User;
import com.f1v3.api.exception.PostNotFound;
import com.f1v3.api.exception.UserNotFound;
import com.f1v3.api.repository.post.PostRepository;
import com.f1v3.api.repository.user.UserRepository;
import com.f1v3.api.request.post.PostCreate;
import com.f1v3.api.request.post.PostEdit;
import com.f1v3.api.request.post.PostSearch;
import com.f1v3.api.response.PagingResponse;
import com.f1v3.api.response.PostCreateResponse;
import com.f1v3.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return new PostResponse(post);
    }

    /**
     * 페이징 처리된 게시글 다중 조회 메서드입니다.
     */
    @Transactional(readOnly = true)
    public PagingResponse<PostResponse> getList(PostSearch postSearch) {
        Page<Post> postPage = postRepository.getList(postSearch);
        return new PagingResponse<>(postPage, PostResponse.class);
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
