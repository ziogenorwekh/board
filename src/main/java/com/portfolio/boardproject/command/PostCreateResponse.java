package com.portfolio.boardproject.command;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PostCreateResponse {

    private final UUID postId;
    private final LocalDateTime createdAt;

    public PostCreateResponse(UUID postId, LocalDateTime createdAt) {
        this.postId = postId;
        this.createdAt = createdAt;
    }
}
