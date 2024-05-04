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


    @Override
    public List<Post> getList(PostSearch postSearch) {
        QPost post = QPost.post;

        return from(post)
                .orderBy(post.id.desc())
                .limit(postSearch.getSize())
                .offset((long) (postSearch.getPage() - 1) * postSearch.getSize())
                .fetch();
    }
}
