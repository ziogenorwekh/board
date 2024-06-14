package com.portfolio.boardproject.service;

import com.portfolio.boardproject.command.post.*;
import com.portfolio.boardproject.domain.Post;
import com.portfolio.boardproject.jpa.PostEntity;
import com.portfolio.boardproject.jpa.PostRepository;
import com.portfolio.boardproject.mapper.PostMapper;
import com.portfolio.boardproject.valueobject.Contents;
import com.portfolio.boardproject.valueobject.Title;
import com.portfolio.boardproject.valueobject.UserId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public PostCreateResponse createPost(PostCreateCommand postCreateCommand) {

        Post post = Post.initialize(postCreateCommand);
        PostEntity postEntity = postMapper.toPostEntity(post);
        PostEntity savedPost = postRepository.save(postEntity);

        return new PostCreateResponse(savedPost.getPostId(), savedPost.getCreatedAt());
    }

    @Override
    public PostTrackQueryResponse findOnePost(PostTrackQuery postTrackQuery) {
        PostEntity postEntity = postRepository.findById(postTrackQuery.getPostId())
                .orElseThrow(() -> new RuntimeException());
        return postMapper.toPostTrackQueryResponse(postEntity);
    }

    @Override
    public List<PostTrackQueryResponse> findAllPosts() {
        List<PostTrackQueryResponse> postTrackQueryResponses = new ArrayList<>();
        postRepository.findAll().stream().forEach(postEntity -> postTrackQueryResponses
                .add(postMapper.toPostTrackQueryResponse(postEntity)));
        return postTrackQueryResponses;
    }

    @Override
    public void updatePost(PostUpdateCommand postUpdateCommand) {
        PostEntity postEntity = postRepository.findById(postUpdateCommand.getPostId()).orElseThrow(() -> {
            throw new RuntimeException();
        });

        Post post = new Post(postEntity);
        post.updateTitle(new Title(postUpdateCommand.getTitle()), new UserId(postUpdateCommand.getUserId()));
        post.updateContents(new Contents(postUpdateCommand.getContent()), new UserId(postUpdateCommand.getUserId()));

        PostEntity updatedPostEntity = postMapper.toPostEntity(post);

        postRepository.save(updatedPostEntity);
    }

    @Override
    public void deletePost(PostDeleteCommand postDeleteCommand) {
        PostEntity postEntity = postRepository
                .findById(postDeleteCommand.getPostId()).orElseThrow(() -> new RuntimeException());
        Post post = new Post(postEntity);
        post.delete(new UserId(postDeleteCommand.getUserId()));
        postRepository.delete(postEntity);
    }
}
