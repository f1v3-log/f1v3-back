package com.f1v3.api.service;

import com.f1v3.api.domain.Comment;
import com.f1v3.api.domain.Post;
import com.f1v3.api.exception.PostNotFound;
import com.f1v3.api.repository.comment.CommentRepository;
import com.f1v3.api.repository.post.PostRepository;
import com.f1v3.api.request.comment.CommentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void write(Long postId, CommentCreate request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        Comment comment = Comment.builder()
                .post(post)
                .author(request.getAuthor())
                .password(encryptedPassword)
                .content(request.getContent())
                .build();

        post.addComment(comment);
    }

}
