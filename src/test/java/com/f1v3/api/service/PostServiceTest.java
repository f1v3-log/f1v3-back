package com.f1v3.api.service;

import com.f1v3.api.domain.Post;
import com.f1v3.api.domain.User;
import com.f1v3.api.exception.PostNotFound;
import com.f1v3.api.repository.post.PostRepository;
import com.f1v3.api.repository.user.UserRepository;
import com.f1v3.api.request.post.PostCreate;
import com.f1v3.api.request.post.PostEdit;
import com.f1v3.api.request.post.PostSearch;
import com.f1v3.api.response.PagingResponse;
import com.f1v3.api.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService postService;


    @BeforeEach
    void clear() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성 테스트")
    void writePost() {
        // given
        User user = User.builder()
                .email("f1v3@kakao.com")
                .password("1234")
                .name("seungjo")
                .build();

        userRepository.save(user);


        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        postService.write(user.getId(), postCreate);

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
    @DisplayName("글 1개 조회 - 존재하지 않는 글")
    void getOnePost_Fail() {
        // given
        Post post = Post.builder()
                .title("f1v3")
                .content("hello, users!")
                .build();

        postRepository.save(post);


        // expected
        assertThrows(PostNotFound.class,
                () -> postService.get(post.getId() + 1L));
    }

    @Test
    @DisplayName("글 첫 페이지 조회")
    void getPostPagination() {
        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("f1v3 title - " + i)
                        .content("구구가가 - " + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        // when
        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();

        PagingResponse<PostResponse> posts = postService.getList(postSearch);

        // then
        assertEquals(10L, posts.getSize());
        assertEquals("f1v3 title - 30", posts.getItems().get(0).getTitle());
    }

    @Test
    @DisplayName("게시글 제목 수정")
    void editPostTitle() {
        // given
        Post post = Post.builder().
                title("f1v3")
                .content("가나다라")
                .build();

        postRepository.save(post);

        // when
        PostEdit postEdit = PostEdit.builder()
                .title("승조정")
                .content("가나다라")
                .build();

        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않아요. id = " + post.getId()));

        assertEquals("승조정", changedPost.getTitle());
        assertEquals("가나다라", changedPost.getContent());
    }

    @Test
    @DisplayName("게시글 제목 수정 - 존재하지 않는 글")
    void editPostTitle_Fail() {
        // given
        Post post = Post.builder().
                title("f1v3")
                .content("가나다라")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("승조정")
                .content("가나다라")
                .build();

        // expected

        assertThrows(PostNotFound.class,
                () -> postService.edit(post.getId() + 1L, postEdit));
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePost() {
        // given
        Post post = Post.builder().
                title("f1v3")
                .content("가나다라")
                .build();

        postRepository.save(post);

        // when
        postService.delete(post.getId());

        // then
        assertEquals(0, postRepository.count());
    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void deletePost_Fail() {
        // given
        Post post = Post.builder().
                title("f1v3")
                .content("가나다라")
                .build();

        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class,
                () -> postService.delete(post.getId() + 1L));
    }
}