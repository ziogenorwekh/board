package com.portfolio.boardproject.command.user;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserTrackQuery {

    private final UUID userId;

    public UserTrackQuery(UUID userId) {
        this.userId = userId;
    }
}
