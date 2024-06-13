package com.portfolio.boardproject.command;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PostUpdateCommand {

    private final UUID postId;
    private final String title;
    private final String content;
    private final UUID userId;

    public PostUpdateCommand(UUID postId, String title, String content, UUID userId) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
