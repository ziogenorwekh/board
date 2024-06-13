package com.portfolio.boardproject.command;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PostCreateCommand {

    private final String title;
    private final String content;
    private final UUID userId;

    public PostCreateCommand(String title, String content, UUID userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
