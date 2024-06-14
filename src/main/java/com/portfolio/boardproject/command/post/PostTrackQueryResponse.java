package com.portfolio.boardproject.command.post;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PostTrackQueryResponse {

    private final UUID postId;
    private final UUID userId;
    private final String poster;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public PostTrackQueryResponse(UUID postId, UUID userId, String poster,
                                  String title, String contents,
                                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.userId = userId;
        this.poster = poster;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
