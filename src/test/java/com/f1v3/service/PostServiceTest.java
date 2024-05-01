package com.f1v3.service;

import com.f1v3.domain.Post;
import com.f1v3.dto.request.PostCreateRequest;
import com.f1v3.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
                .title("this is title!!")
                .content("content is empty :)")
                .build();

        postRepository.save(requestPost);

        // when
        Post post = postService.get(requestPost.getId());

        // then
        assertNotNull(post);
        assertEquals(1L, postRepository.count());
        Post responsePost = postRepository.findAll().get(0);
        assertEquals("this is title!!", responsePost.getTitle());
        assertEquals("content is empty :)", responsePost.getContent());
    }
}