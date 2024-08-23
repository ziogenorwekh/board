package com.portfolio.boardproject.api;

import com.portfolio.boardproject.command.user.*;
import com.portfolio.boardproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping(path = "/api")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Retrieve User", description = "Retrieve user details by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of user details",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserTrackQueryResponse.class)),
                    }),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @RequestMapping(path = "/users/{userId}",method = RequestMethod.GET)
    public ResponseEntity<UserTrackQueryResponse> retrieveUser(@PathVariable UUID userId) {
        log.info("Retrieving user details by user ID {}", userId);
        UserTrackQuery userTrackQuery = new UserTrackQuery(userId);
        UserTrackQueryResponse trackQueryResponse = userService.findUserById(userTrackQuery);
        return ResponseEntity.ok(trackQueryResponse);
    }

    @Operation(summary = "Create User", description = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserCreateResponse.class)),
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public ResponseEntity<UserCreateResponse> createUser(@RequestBody UserCreateCommand userCreateCommand) {
        log.info("create user {}", userCreateCommand);
        UserCreateResponse createResponse = userService.createUser(userCreateCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse);
    }

    @Operation(summary = "Update User", description = "Update user details by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully updated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @RequestMapping(path = "/users/{userId}",method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(@PathVariable UUID userId, @RequestBody UserUpdateCommand userUpdateCommand) {
        log.info("update user {}", userId);
        userUpdateCommand.setUserId(userId);
        userService.updateUser(userUpdateCommand);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete User", description = "Delete user by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @RequestMapping(path = "/users/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        log.info("delete user {}", userId);
        UserDeleteCommand userDeleteCommand = new UserDeleteCommand(userId);
        userService.deleteUser(userDeleteCommand);
        return ResponseEntity.noContent().build();
    }



}
