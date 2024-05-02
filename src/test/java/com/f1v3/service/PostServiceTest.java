package com.f1v3.service;

import com.f1v3.domain.Post;
import com.f1v3.repository.PostRepository;
import com.f1v3.request.PostCreateRequest;
import com.f1v3.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @BeforeEach
    void clear() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성 테스트")
    void writePost() {
        // given
        PostCreateRequest postCreate = PostCreateRequest.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        postService.write(postCreate);

        // then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void getOnePost() {
        // given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(requestPost);

        // when
        PostResponse response = postService.get(requestPost.getId());

        // then
        assertNotNull(response);
        assertEquals(1L, postRepository.count());
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void getManyPost() {
        // given
        Post requestPost1 = Post.builder()
                .title("foo1")
                .content("bar2")
                .build();

        postRepository.save(requestPost1);

        Post requestPost2 = Post.builder()
                .title("foo2")
                .content("bar2")
                .build();

        postRepository.save(requestPost2);

        // when
        List<PostResponse> posts = postService.getList();

        // then
        assertEquals(2, posts.size());
    }
}