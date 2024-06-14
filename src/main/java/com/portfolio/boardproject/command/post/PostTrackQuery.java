package com.portfolio.boardproject.command.post;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PostTrackQuery {

    @NotNull
    private final UUID postId;

    public PostTrackQuery(UUID postId) {
        this.postId = postId;
    }
}
