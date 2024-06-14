package com.portfolio.boardproject.command.post;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class PostDeleteCommand {

    @NotNull
    private final UUID userId;
    @NotNull
    @Setter
    private UUID postId;

    public PostDeleteCommand(UUID userId) {
        this.userId = userId;
    }
}
