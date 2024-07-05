package com.f1v3.api.repository.post;

import com.f1v3.api.domain.Post;
import com.f1v3.api.request.post.PostSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PostRepositoryCustom {

    /**
     * 게시글 리스트 조회 메서드입니다.
     *
     * @param postSearch 게시글 검색 조건
     * @return 게시글 리스트
     */
    Page<Post> getList(PostSearch postSearch);
}
