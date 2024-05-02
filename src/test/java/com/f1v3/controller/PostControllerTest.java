package com.f1v3.controller;

import com.f1v3.domain.Post;
import com.f1v3.repository.PostRepository;
import com.f1v3.request.PostCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void clear() {
        postRepository.deleteAll();
    }


    @Test
    @DisplayName("/posts 요청시 title, content 값이 정상이면 201(CREATED) 반환")
    void post_response_test() throws Exception {

        // given
        PostCreateRequest request = PostCreateRequest.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());

    }

    @Test
    @DisplayName("/posts 요청시 title 값은 필수다.")
    void post_null_check() throws Exception {

        // given
        PostCreateRequest request = PostCreateRequest.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("title은 필수 값입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다.")
    void post_db_test() throws Exception {

        // given
        PostCreateRequest request = PostCreateRequest.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andDo(print());

        // then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void testGet() throws Exception {
        // given
        Post post = postRepository.save(Post.builder()
                .title("123456789012345")
                .content("bar")
                .build());

        // expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value(post.getContent()))
                .andDo(print());
    }

    @Test
    @DisplayName("글 1개 조회")
    void testGetList() throws Exception {
        // given
        Post post1 = Post.builder()
                .title("title_1")
                .content("content_1")
                .build();


        Post post2 = Post.builder()
                .title("title_2")
                .content("content_2")
                .build();

        postRepository.saveAll(List.of(post1, post2));

        // expected
        mockMvc.perform(get("/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.length()", is(2)),
                        jsonPath("$[0].id").value(post1.getId()),
                        jsonPath("$[0].title").value(post1.getTitle()),
                        jsonPath("$[0].content").value(post1.getContent()),
                        jsonPath("$[1].id").value(post2.getId()),
                        jsonPath("$[1].title").value(post2.getTitle()),
                        jsonPath("$[1].content").value(post2.getContent())
                )
                .andDo(print());
    }

}