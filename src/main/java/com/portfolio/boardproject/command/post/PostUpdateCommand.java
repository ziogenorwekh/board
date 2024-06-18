package com.portfolio.boardproject.command.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class PostUpdateCommand {

    @Setter
    @NotNull
    @Schema(description = "Unique identifier of the post to update", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID postId;

    @NotBlank(message = "Title must not be blank")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    @Schema(description = "Title of the post", example = "Updated Post Title")
    private final String title;

    @NotBlank(message = "Content must not be blank")
    @Schema(description = "Content of the post", example = "This is an updated post content.")
    private final String content;

    @NotNull
    @Schema(description = "Unique identifier of the user who updates the post", example = "123e4567-e89b-12d3-a456-426614174001")
    private final UUID userId;

    public PostUpdateCommand(String title, String content, UUID userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
