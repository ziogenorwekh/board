package com.portfolio.boardproject.service;

import com.portfolio.boardproject.command.*;

import java.util.List;

public interface PostService {

    PostCreateResponse createPost(PostCreateCommand postCreateCommand);

    PostTrackQueryResponse findOnePost(PostTrackQuery postTrackQuery);

    List<PostTrackQueryResponse> findAllPosts();

    void updatePost(PostUpdateCommand postUpdateCommand);

    void deletePost(PostDeleteCommand postDeleteCommand);
}
