package com.f1v3.repository;

import com.f1v3.domain.Post;
import com.f1v3.request.PostSearch;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
