package com.portfolio.boardproject.usertest;

import com.portfolio.boardproject.jpa.RoleEntity;
import com.portfolio.boardproject.jpa.RoleEnum;
import com.portfolio.boardproject.jpa.UserEntity;
import com.portfolio.boardproject.jpa.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Random;

@DataJpaTest(showSql = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2,
        replace = AutoConfigureTestDatabase.Replace.ANY)
@EnableJpaAuditing
public class UserCRUDTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public void init() {
        String[] firstNames = {"John", "Jane", "Alex", "Chris", "Taylor"};
        String[] lastNames = {"Smith", "Doe", "Brown", "Johnson", "White"};
        String[] domains = {"example.com", "test.com", "demo.com"};
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            String firstName = firstNames[random.nextInt(firstNames.length)];
            String lastName = lastNames[random.nextInt(lastNames.length)];
            String username = firstName.toLowerCase() + lastName.toLowerCase() + i;
            String password = "password" + i;
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + domains[random.nextInt(domains.length)];

            UserEntity user = UserEntity.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .build();
            RoleEntity roleEntity = RoleEntity.builder()
                    .roleName(RoleEnum.USER).user(user).build();
            user.getRole().add(roleEntity);
            userRepository.save(user);
        }
    }

    @AfterAll
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유저 생성")
    public void createUserTests() {

    }
}
