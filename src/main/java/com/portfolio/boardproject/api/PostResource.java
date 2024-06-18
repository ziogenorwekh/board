package com.portfolio.boardproject.api;

import com.portfolio.boardproject.command.post.*;
import com.portfolio.boardproject.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Create Post", description = "Create a new post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostCreateResponse.class)),
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid post data")
    })
    @RequestMapping(path = "/posts", method = RequestMethod.POST)
    public ResponseEntity<PostCreateResponse> createPost(@RequestBody PostCreateCommand postCreateCommand) {
        PostCreateResponse createResponse = postService.createPost(postCreateCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse);
    }
    @Operation(summary = "Update Post", description = "Update post details by post ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post successfully updated"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @RequestMapping(path = "/posts/{postId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updatePost(@PathVariable("postId") UUID postId, @RequestBody PostUpdateCommand postUpdateCommand) {
        postUpdateCommand.setPostId(postId);
        postService.updatePost(postUpdateCommand);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Retrieve Post", description = "Retrieve post details by post ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of post details",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostTrackQueryResponse.class)),
                    }),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @RequestMapping(path = "/posts/{postId}", method = RequestMethod.GET)
    public ResponseEntity<PostTrackQueryResponse> retrievePost(@PathVariable("postId") UUID postId) {
        PostTrackQuery postTrackQuery = new PostTrackQuery(postId);
        PostTrackQueryResponse queryResponse = postService.findOnePost(postTrackQuery);
        return ResponseEntity.ok(queryResponse);
    }

    @Operation(summary = "Delete Post", description = "Delete post by post ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @RequestMapping(path = "/posts/{postId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePost(@PathVariable("postId") UUID postId,
                                           @RequestBody PostDeleteCommand postDeleteCommand) {
        postDeleteCommand.setPostId(postId);
        postService.deletePost(postDeleteCommand);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Retrieve All Posts", description = "Retrieve all posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of posts",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostTrackQueryResponse.class)),
                    }),
            @ApiResponse(responseCode = "404", description = "No posts found")
    })
    @RequestMapping(path = "/posts", method = RequestMethod.GET)
    public ResponseEntity<List<PostTrackQueryResponse>> retrievePosts() {
        List<PostTrackQueryResponse> postTrackQueryResponseList = postService.findAllPosts();
        return ResponseEntity.ok(postTrackQueryResponseList);
    }

}
