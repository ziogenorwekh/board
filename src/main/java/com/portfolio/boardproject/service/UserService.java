package com.portfolio.boardproject.service;

import com.portfolio.boardproject.command.user.*;
import jakarta.validation.Valid;

public interface UserService {

    UserCreateResponse createUser(@Valid UserCreateCommand userCreateCommand);

    UserTrackQueryResponse findUserById(@Valid UserTrackQuery userTrackQuery);

    void updateUser(@Valid UserUpdateCommand userUpdateCommand);

    void deleteUser(@Valid UserDeleteCommand userDeleteCommand);
}
