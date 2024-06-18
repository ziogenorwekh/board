package com.portfolio.boardproject.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.UUID;

@Getter
public class LoginResponseVO {

    @Schema(description = "User ID generated for the user", example = "123e4567-e89b-12d3-a456-426614174000")
    private final UUID userId;

    @Schema(description = "JWT token for authentication", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private final String token;

    public LoginResponseVO(UUID userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
