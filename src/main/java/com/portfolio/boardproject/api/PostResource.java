package com.portfolio.boardproject.api;

import com.portfolio.boardproject.command.post.*;
import com.portfolio.boardproject.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api")
public class PostResource {

    private final PostService postService;

    public PostResource(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(path = "/posts", method = RequestMethod.POST)
    public ResponseEntity<PostCreateResponse> createPost(@RequestBody PostCreateCommand postCreateCommand) {
        PostCreateResponse createResponse = postService.createPost(postCreateCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse);
    }

    @RequestMapping(path = "/posts/{postId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updatePost(@PathVariable("postId") UUID postId, @RequestBody PostUpdateCommand postUpdateCommand) {
        postUpdateCommand.setPostId(postId);
        postService.updatePost(postUpdateCommand);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/posts/{postId}", method = RequestMethod.GET)
    public ResponseEntity<PostTrackQueryResponse> retrievePost(@PathVariable("postId") UUID postId) {
        PostTrackQuery postTrackQuery = new PostTrackQuery(postId);
        PostTrackQueryResponse queryResponse = postService.findOnePost(postTrackQuery);
        return ResponseEntity.ok(queryResponse);
    }

    @RequestMapping(path = "/posts/{postId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePost(@PathVariable("postId") UUID postId,
                                           @RequestBody PostDeleteCommand postDeleteCommand) {
        postDeleteCommand.setPostId(postId);
        postService.deletePost(postDeleteCommand);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/posts", method = RequestMethod.GET)
    public ResponseEntity<List<PostTrackQueryResponse>> retrievePosts() {
        List<PostTrackQueryResponse> postTrackQueryResponseList = postService.findAllPosts();
        return ResponseEntity.ok(postTrackQueryResponseList);
    }

}
