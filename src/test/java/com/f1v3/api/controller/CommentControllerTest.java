package com.f1v3.api.controller;

import com.f1v3.api.domain.Comment;
import com.f1v3.api.domain.Post;
import com.f1v3.api.domain.User;
import com.f1v3.api.repository.comment.CommentRepository;
import com.f1v3.api.repository.post.PostRepository;
import com.f1v3.api.repository.user.UserRepository;
import com.f1v3.api.request.comment.CommentCreate;
import com.f1v3.api.request.comment.CommentDelete;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("댓글 작성")
    void writeComment() throws Exception {

        // given
        User user = User.builder()
                .name("승조")
                .email("f1v3@gmail.com")
                .password("1212")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("123456789012345")
                .content("bar")
                .user(user)
                .build();

        postRepository.save(post);

        CommentCreate request = CommentCreate.builder()
                .author("sj")
                .password("123456")
                .content("동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts/{postId}/comments", post.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated());

        assertEquals(1L, commentRepository.count());
        Comment savedComment = commentRepository.findAll().get(0);

        assertEquals(request.getAuthor(), savedComment.getAuthor());
        assertEquals(request.getContent(), savedComment.getContent());
        assertNotEquals(request.getPassword(), savedComment.getPassword());
        assertTrue(passwordEncoder.matches(request.getPassword(), savedComment.getPassword()));
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() throws Exception {

        // given
        User user = User.builder()
                .name("승조")
                .email("f1v3@gmail.com")
                .password("1212")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("123456789012345")
                .content("bar")
                .user(user)
                .build();

        postRepository.save(post);

        String encryptedPassword = passwordEncoder.encode("123456");

        Comment comment = Comment.builder()
                .post(post)
                .author("seungjo")
                .password(encryptedPassword)
                .content("동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세")
                .build();

        commentRepository.save(comment);

        CommentDelete request = new CommentDelete();
        ReflectionTestUtils.setField(request, "password", "123456");

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/comments/{commentId}/delete", comment.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
