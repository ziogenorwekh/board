package com.portfolio.boardproject.command.user;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserDeleteCommand {

    private final UUID userId;

    public UserDeleteCommand(UUID userId) {
        this.userId = userId;
    }
}
