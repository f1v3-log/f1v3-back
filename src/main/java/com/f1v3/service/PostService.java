package com.f1v3.service;

import com.f1v3.domain.Post;
import com.f1v3.dto.request.PostCreateRequest;
import com.f1v3.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreateRequest postCreate) {
        // post -> entity로 변환

        Post post = new Post(postCreate.getTitle(), postCreate.getContent());
        postRepository.save(post);
    }
}
