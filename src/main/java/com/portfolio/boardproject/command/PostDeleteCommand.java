package com.portfolio.boardproject.command;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PostDeleteCommand {

    private final UUID userId;
    private final UUID postId;

    public PostDeleteCommand(UUID userId, UUID postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
