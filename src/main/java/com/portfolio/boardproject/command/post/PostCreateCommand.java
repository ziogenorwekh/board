package com.portfolio.boardproject.command.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PostCreateCommand {

    @Schema(description = "Title of the post", example = "Sample Title")
    @NotBlank(message = "Title must not be blank")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private final String title;

    @Schema(description = "Content of the post", example = "Sample content for the post")
    @NotBlank(message = "Content must not be blank")
    private final String content;

    @Schema(description = "User ID of the post creator", example = "123e4567-e89b-12d3-a456-426614174000")
    @NotNull
    private final UUID userId;

    public PostCreateCommand(String title, String content, UUID userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
