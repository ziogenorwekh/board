package com.portfolio.boardproject.command;

import lombok.Getter;

@Getter
public class UserCreateCommand {

    private final String username;
    private final String password;
    private final String email;

    public UserCreateCommand(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
