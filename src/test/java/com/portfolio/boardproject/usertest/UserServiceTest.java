package com.portfolio.boardproject.usertest;

import com.portfolio.boardproject.command.UserCreateCommand;
import com.portfolio.boardproject.mapper.UserMapper;
import com.portfolio.boardproject.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2,
        replace = AutoConfigureTestDatabase.Replace.ANY)
@EnableJpaAuditing
public class UserServiceTest {

    @Mock
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    @DisplayName("유저 생성")
    public void createUserTest() {
    }
}
