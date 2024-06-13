package com.portfolio.boardproject.command.user;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserUpdateCommand {

    private final UUID userId;
    private final String currentPassword;
    private final String newPassword;

    public UserUpdateCommand(UUID userId, String currentPassword, String newPassword) {
        this.userId = userId;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}
