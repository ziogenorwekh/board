package com.portfolio.boardproject.command.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserTrackQuery {

    @NotNull
    @Schema(description = "User ID", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    private final UUID userId;

    public UserTrackQuery(UUID userId) {
        this.userId = userId;
    }
}
