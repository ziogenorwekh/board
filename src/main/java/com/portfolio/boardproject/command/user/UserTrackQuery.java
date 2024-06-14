package com.portfolio.boardproject.command.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserTrackQuery {

    @NotNull
    private final UUID userId;

    public UserTrackQuery(UUID userId) {
        this.userId = userId;
    }
}
