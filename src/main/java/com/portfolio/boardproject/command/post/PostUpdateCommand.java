package com.portfolio.boardproject.command.post;

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
    private UUID postId;
    @NotBlank(message = "Title must not be blank")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private final String title;
    @NotBlank(message = "Content must not be blank")
    private final String content;
    @NotNull
    private final UUID userId;

    public PostUpdateCommand(String title, String content, UUID userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
