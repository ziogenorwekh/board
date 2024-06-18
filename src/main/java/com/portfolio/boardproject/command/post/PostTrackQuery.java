package com.portfolio.boardproject.command.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PostTrackQuery {

    @NotNull
    @Schema(description = "Unique identifier of the post to track", example = "123e4567-e89b-12d3-a456-426614174001")
    private final UUID postId;

    public PostTrackQuery(UUID postId) {
        this.postId = postId;
    }
}
