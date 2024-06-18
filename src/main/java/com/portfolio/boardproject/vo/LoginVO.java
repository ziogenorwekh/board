package com.portfolio.boardproject.vo;

import lombok.Getter;

@Getter
public class LoginVO {

    private final String username;
    private final String password;

    public LoginVO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
