package com.portfolio.boardproject.api;

import com.portfolio.boardproject.command.user.*;
import com.portfolio.boardproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/users/{userId}",method = RequestMethod.GET)
    public ResponseEntity<UserTrackQueryResponse> retrieveUser(@PathVariable UUID userId) {
        UserTrackQuery userTrackQuery = new UserTrackQuery(userId);
        UserTrackQueryResponse trackQueryResponse = userService.findUserById(userTrackQuery);
        return ResponseEntity.ok(trackQueryResponse);
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public ResponseEntity<UserCreateResponse> createUser(@RequestBody UserCreateCommand userCreateCommand) {
        UserCreateResponse createResponse = userService.createUser(userCreateCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse);
    }

    @RequestMapping(path = "/users/{userId}",method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(@PathVariable UUID userId, @RequestBody UserUpdateCommand userUpdateCommand) {

        userUpdateCommand.setUserId(userId);
        userService.updateUser(userUpdateCommand);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/users/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        UserDeleteCommand userDeleteCommand = new UserDeleteCommand(userId);
        userService.deleteUser(userDeleteCommand);
        return ResponseEntity.noContent().build();
    }



}
