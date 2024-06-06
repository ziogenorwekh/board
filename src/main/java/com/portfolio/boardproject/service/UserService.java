package com.portfolio.boardproject.service;

import com.portfolio.boardproject.command.UserCreateCommand;

public interface UserService {

    void createUser(UserCreateCommand userCreateCommand);
}
