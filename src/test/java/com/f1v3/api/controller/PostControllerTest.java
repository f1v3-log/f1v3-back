package com.f1v3.api.controller;

import com.f1v3.api.config.F1v3logMockUser;
import com.f1v3.api.domain.Post;
import com.f1v3.api.domain.User;
import com.f1v3.api.repository.post.PostRepository;
import com.f1v3.api.repository.user.UserRepository;
import com.f1v3.api.request.post.PostCreate;
import com.f1v3.api.request.post.PostEdit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void clear() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    @F1v3logMockUser
    @DisplayName("게시글 작성 요청시 title, content 값이 정상이면 201(CREATED) 반환")
    void post_response_test() throws Exception {

        // given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @F1v3logMockUser
    @DisplayName("게시글 작성시 제목에 '광고'는 포함될 수 없다.")
    void post_response_fail() throws Exception {

        // given
        PostCreate request = PostCreate.builder()
                .title("[광고] 무료 블로그%!#@$")
                .content("$무료로 글을 작성할 수 있는 블로그$")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 작성 요청시 title 값은 필수다.")
    void post_null_check() throws Exception {

        // given
        PostCreate request = PostCreate.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("title은 필수 값입니다."))
                .andDo(print());
    }

    @Test
    @F1v3logMockUser
    @DisplayName("게시글 작성 요청시 DB에 값이 저장된다.")
    void post_db_test() throws Exception {

        // given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
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
    @DisplayName("게시글 1개 조회")
    void testGet() throws Exception {
        // given
        User user = User.builder()
                .name("승조")
                .email("f1v3@gmail.com")
                .password("1212")
                .build();

        userRepository.save(user);

        Post post = postRepository.save(Post.builder()
                .title("123456789012345")
                .content("bar")
                .user(user)
                .build());

        // expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value(post.getContent()))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 조회 실패 - 존재하지 않는 게시글")
    void testGet_Fail() throws Exception {

        // expected
        mockMvc.perform(get("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 여러개 조회")
    void testGetList() throws Exception {
        // given
        User user = User.builder()
                .name("승조")
                .email("f1v3@gmail.com")
                .password("1212")
                .build();

        userRepository.save(user);

        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("f1v3 title - " + i)
                        .content("구구가가 - " + i)
                        .user(user)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        // expected
        mockMvc.perform(get("/posts?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.length()", is(10)),
                        jsonPath("$[0].title").value("f1v3 title - 19"),
                        jsonPath("$[0].content").value("구구가가 - 19")
                )
                .andDo(print());
    }


    @Test
    @DisplayName("게시글 여러개 조회 - 페이지 0으로 요청시 첫 페이지를 가져옴")
    void testGetZeroPage() throws Exception {
        // given
        User user = User.builder()
                .name("승조")
                .email("f1v3@gmail.com")
                .password("1212")
                .build();

        userRepository.save(user);

        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("f1v3 title - " + i)
                        .content("구구가가 - " + i)
                        .user(user)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        // expected
        mockMvc.perform(get("/posts?page=0&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.length()", is(10)),
                        jsonPath("$[0].title").value("f1v3 title - 19"),
                        jsonPath("$[0].content").value("구구가가 - 19")
                )
                .andDo(print());
    }

    @Test
    @F1v3logMockUser
    @DisplayName("게시글 제목 수정")
    void editPostTitle() throws Exception {
        // given
        User user = userRepository.findAll().get(0);

        Post post = Post.builder().
                title("f1v3")
                .content("가나다라")
                .user(user)
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("승조정")
                .content("가나다라")
                .build();

        // expected
        mockMvc.perform(patch("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @F1v3logMockUser
    @DisplayName("게시글 수정 실패 - 존재하지 않는 게시글")
    void editPost_Fail() throws Exception {

        // given
        PostEdit postEdit = PostEdit.builder()
                .title("승조정")
                .content("가나다라")
                .build();

        // expected
        mockMvc.perform(patch("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @F1v3logMockUser
    @DisplayName("게시글 삭제")
    void deletePost() throws Exception {
        // given
        User user = userRepository.findAll().get(0);

        Post post = Post.builder().
                title("f1v3")
                .content("가나다라")
                .user(user)
                .build();

        postRepository.save(post);

        // expected
        mockMvc.perform(delete("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @F1v3logMockUser
    @DisplayName("게시글 삭제 실패 - 존재하지 않는 게시글")
    void deletePost_Fail() throws Exception {

        // expected
        mockMvc.perform(delete("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}