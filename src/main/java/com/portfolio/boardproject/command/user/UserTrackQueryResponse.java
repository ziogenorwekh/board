package com.portfolio.boardproject.command.user;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class UserTrackQueryResponse {

    private final UUID userId;
    private final String username;
    private final String email;
    private final LocalDateTime createdAt;

    @Builder
    public UserTrackQueryResponse(UUID userId, String username, String email, LocalDateTime createdAt) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
    }
}
