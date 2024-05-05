package com.f1v3.repository;

import com.f1v3.domain.Post;
import com.f1v3.domain.QPost;
import com.f1v3.request.PostSearch;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class PostRepositoryImpl extends QuerydslRepositorySupport implements PostRepositoryCustom {

    public PostRepositoryImpl() {
        super(Post.class);
    }

    /**
     * 게시글 리스트 조회 메서드입니다.
     * @param postSearch 게시글 검색 조건
     * @return 게시글 리스트
     */
    @Override
    public List<Post> getList(PostSearch postSearch) {
        QPost post = QPost.post;

        return from(post)
                .orderBy(post.id.desc())
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .fetch();
    }
}
