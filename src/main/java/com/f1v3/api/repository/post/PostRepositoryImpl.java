package com.f1v3.api.repository.post;

import com.f1v3.api.domain.Post;
import com.f1v3.api.domain.QPost;
import com.f1v3.api.request.post.PostSearch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Slf4j
public class PostRepositoryImpl extends QuerydslRepositorySupport implements PostRepositoryCustom {

    public PostRepositoryImpl() {
        super(Post.class);
    }

    /**
     * 게시글 리스트 조회 메서드입니다.
     *
     * @param postSearch 게시글 검색 조건
     * @return 게시글 리스트
     */
    @Override
    public Page<Post> getList(PostSearch postSearch) {
        QPost post = QPost.post;

        Long totalCount = from(post)
                .select(post.count())
                .fetchFirst();

        log.info("totalCount: {}", totalCount);

        List<Post> items = from(post)
                .orderBy(post.id.desc())
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .fetch();

        return new PageImpl<>(items, postSearch.getPageable(), totalCount);
    }
}
