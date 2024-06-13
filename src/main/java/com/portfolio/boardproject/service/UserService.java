package com.portfolio.boardproject.service;

import com.portfolio.boardproject.command.user.*;

public interface UserService {

    UserCreateResponse createUser(UserCreateCommand userCreateCommand);

    UserTrackQueryResponse findUserById(UserTrackQuery userTrackQuery);

    void updateUser(UserUpdateCommand userUpdateCommand);

    void deleteUser(UserDeleteCommand userDeleteCommand);
}
