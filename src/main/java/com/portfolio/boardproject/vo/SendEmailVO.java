package com.portfolio.boardproject.vo;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendEmailVO {

    @Parameter(description = "User email")
    @NotNull
    @Email
    @Schema(description = "User Email", example = "test@example.com")
    private String email;

    public SendEmailVO(String email) {
        this.email = email;
    }
}
