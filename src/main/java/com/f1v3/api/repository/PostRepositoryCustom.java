package com.f1v3.api.repository;

import com.f1v3.api.domain.Post;
import com.f1v3.api.request.PostSearch;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface PostRepositoryCustom {

    /**
     * 게시글 리스트 조회 메서드입니다.
     *
     * @param postSearch 게시글 검색 조건
     * @return 게시글 리스트
     */
    List<Post> getList(PostSearch postSearch);
}
