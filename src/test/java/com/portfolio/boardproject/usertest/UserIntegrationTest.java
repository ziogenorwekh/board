package com.portfolio.boardproject.usertest;


import com.portfolio.boardproject.api.UserResource;
import com.portfolio.boardproject.command.user.*;
import com.portfolio.boardproject.service.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserIntegrationTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserResource userResource;

    private final UUID userId = UUID.randomUUID();

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeEach
    void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("유저 조회 API")
    public void findUserTests() {

        // given
        UUID userId = UUID.randomUUID();
        UserTrackQueryResponse userTrackQueryResponse =
                new UserTrackQueryResponse(userId, "username", "test@gmail.com", LocalDateTime.now());

        Mockito.when(userService.findUserById(Mockito.any(UserTrackQuery.class))).thenReturn(userTrackQueryResponse);

        // when
        ResponseEntity<UserTrackQueryResponse> userTrackQueryResponseResponseEntity = userResource.retrieveUser(userId);
        UserTrackQueryResponse body = userTrackQueryResponseResponseEntity.getBody();

        // then
        Assertions.assertEquals(HttpStatus.OK, userTrackQueryResponseResponseEntity.getStatusCode());
        Assertions.assertEquals(body.getUserId(), userTrackQueryResponse.getUserId());
        Assertions.assertEquals(body.getEmail(), userTrackQueryResponse.getEmail());
        Assertions.assertEquals(body.getUsername(), userTrackQueryResponse.getUsername());
    }

    @Test
    @DisplayName("유저 생성 API")
    public void createUserTests() {

        // given
        UUID userId = UUID.randomUUID();
        UserCreateCommand userCreateCommand = new UserCreateCommand("username", "password", "test@gmail.com");
        UserCreateResponse userCreateResponse = new UserCreateResponse(userId, "username", "test@gmail.com", LocalDateTime.now());
        Mockito.when(userService.createUser(userCreateCommand)).thenReturn(userCreateResponse);
        // when
        ResponseEntity<UserCreateResponse> user = userResource.createUser(userCreateCommand);
        // then

        UserCreateResponse userBody = user.getBody();
        Assertions.assertEquals(HttpStatus.CREATED, user.getStatusCode());
        Assertions.assertEquals(userCreateResponse, userBody);
        Assertions.assertEquals(userId, userBody.getUserId());
    }

    @Test
    @DisplayName("유저 생성 Validation 테스트")
    public void userCreateValidationTest() {
        // given
        UserCreateCommand userCreateCommand = new UserCreateCommand("user", "pwd", "test@gmail.com");
        // when
        Set<ConstraintViolation<UserCreateCommand>> validated = validator.validate(userCreateCommand);
        // then
        Assertions.assertTrue(!validated.isEmpty());
        validated.forEach(error -> Assertions.assertEquals("Password must be between 8 and 20 characters",
                error.getMessage()));
    }

    @Test
    @DisplayName("유저 수정 API")
    public void updateUserTests() {
        // given

        UUID userId = UUID.randomUUID();
        UserUpdateCommand userUpdateCommand = new UserUpdateCommand("password", "newPassword");
        userUpdateCommand.setUserId(userId);

        Mockito.doNothing().when(userService).updateUser(userUpdateCommand);
        // when
        ResponseEntity<Void> responseEntity = userResource.updateUser(userId, userUpdateCommand);
        // then
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
    @Test
    @DisplayName("유저 삭제 API")
    public void userDeleteTests() {

        // given
        UUID userId = UUID.randomUUID();
        Mockito.doNothing().when(userService).deleteUser(Mockito.any(UserDeleteCommand.class));

        // when
        ResponseEntity<Void> response = userResource.deleteUser(userId);

        // then
        ArgumentCaptor<UserDeleteCommand> captor = ArgumentCaptor.forClass(UserDeleteCommand.class);
        Mockito.verify(userService).deleteUser(captor.capture());
        Assertions.assertEquals(userId, captor.getValue().getUserId());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
