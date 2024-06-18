package com.portfolio.boardproject.vo;

import lombok.Getter;

import java.util.UUID;

@Getter
public class LoginResponseVO {

    private final UUID userId;
    private final String token;

    public LoginResponseVO(UUID userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
