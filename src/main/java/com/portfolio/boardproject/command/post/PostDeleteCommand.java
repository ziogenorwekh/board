package com.portfolio.boardproject.command.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class PostDeleteCommand {

    @NotNull
    @Schema(description = "User ID of the user who is deleting the post", example = "123e4567-e89b-12d3-a456-426614174000")
    private final UUID userId;

    @NotNull
    @Schema(description = "Unique identifier of the post to be deleted", example = "123e4567-e89b-12d3-a456-426614174001")
    @Setter
    private UUID postId;

    public PostDeleteCommand(UUID userId) {
        this.userId = userId;
    }
}
