package com.portfolio.boardproject.command.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserDeleteCommand {

    @NotNull
    private final UUID userId;

    public UserDeleteCommand(UUID userId) {
        this.userId = userId;
    }
}
