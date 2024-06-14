package com.portfolio.boardproject.command.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserCreateCommand {


    @NotBlank(message = "Username is mandatory")
    @Size(min = 4, max = 15, message = "Username must be between 4 and 15 characters")
    private final String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private final String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private final String email;

    public UserCreateCommand(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
