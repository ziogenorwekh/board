package com.portfolio.boardproject.command;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserCreateResponse {


    private final String username;
    private final String email;
    private final LocalDateTime createdAt;

    @Builder
    public UserCreateResponse(String username, String email, LocalDateTime createdAt) {
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
    }
}
