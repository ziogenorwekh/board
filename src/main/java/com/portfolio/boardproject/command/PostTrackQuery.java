package com.portfolio.boardproject.command;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PostTrackQuery {

    private final UUID postId;

    public PostTrackQuery(UUID postId) {
        this.postId = postId;
    }
}
