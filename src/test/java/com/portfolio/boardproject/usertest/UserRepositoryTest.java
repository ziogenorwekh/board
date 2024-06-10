package com.portfolio.boardproject.usertest;

import com.portfolio.boardproject.domain.Role;
import com.portfolio.boardproject.domain.User;
import com.portfolio.boardproject.jpa.RoleEntity;
import com.portfolio.boardproject.jpa.RoleEnum;
import com.portfolio.boardproject.jpa.UserEntity;
import com.portfolio.boardproject.jpa.UserRepository;
import com.portfolio.boardproject.mapper.UserMapper;
import com.portfolio.boardproject.valueobject.Password;
import com.portfolio.boardproject.valueobject.UserId;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;
import java.util.UUID;

@DataJpaTest(showSql = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2,
        replace = AutoConfigureTestDatabase.Replace.ANY)
@EnableJpaAuditing
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private UserMapper userMapper;
    private UUID userId;
    private final String password = "password";

    @BeforeAll
    public void beforeAll() {
        userId = UUID.randomUUID();
        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .username("testuser")
                .password(password)
                .email("test@test.com")
                .enabled(true)
                .role(new ArrayList<>())
                .build();
        RoleEntity role = new RoleEntity(RoleEnum.USER, userEntity);
        userEntity.getRole().add(role);
        userRepository.save(userEntity);
    }

    @BeforeEach
    public void beforeEach() {
        userMapper = new UserMapper();
    }

    @AfterEach
    public void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유저 생성 및 삭제")
    public void createUserTest() {

        // given
        UUID userId = UUID.randomUUID();
        UserEntity userEntity = UserEntity.builder().username("HelloWorld")
                .password("pwd")
                .email("HelloWorld@example.com")
                .enabled(false)
                .role(new ArrayList<>())
                .id(userId)
                .build();
        RoleEntity role = new RoleEntity(RoleEnum.USER, userEntity);
        userEntity.getRole().add(role);
        // when
        userRepository.save(userEntity);

        // then

        Assertions.assertEquals(2, userRepository.findAll().size());

        // when

        UserEntity user = userRepository.findById(userId).get();
        Assertions.assertEquals(1, user.getRole().size());
        userRepository.delete(user);

        // then
        Assertions.assertEquals(1, userRepository.findAll().size());
    }

    @Test
    @DisplayName("유저 수정")
    public void updateUserTest() {
        // given
        UserEntity userEntity = userRepository.findById(userId).get();
        String newPassword = "newPassword";
        String currentPassword = "password";

        // when

        User domainUser = new User(userEntity);

        domainUser.updatePassword(new Password(currentPassword), new Password(newPassword));

        // then
        UserMapper userMapper = new UserMapper();
        UserEntity updated = userMapper.toUserEntity(domainUser);
        userRepository.save(updated);

        UserEntity updatedUserEntity = userRepository.findById(userId).get();


        // then
        Assertions.assertEquals(updatedUserEntity.getPassword(), newPassword);
        Assertions.assertEquals(1, userRepository.findAll().size());
    }

    @Test
    @DisplayName("유저 생성 도메인 클래스")
    public void createUserWithDomainTests() {

        // given
        String username = "James";
        String email = "james@example.com";
        String password = "password";
        UUID newId = UUID.randomUUID();

        User user = new User(newId, password, username, email);
        Role role = new Role(new UserId(newId), RoleEnum.USER);
        user.addRole(role);

        // when
        UserEntity userEntity = userMapper.toUserEntity(user);
        userRepository.save(userEntity);

        // then
        Assertions.assertEquals(2, userRepository.findAll().size());

    }

    @Test
    @DisplayName("유저 권한 추가")
    public void userRoleUpdateTests() {
        // given
        UserEntity userEntity = userRepository.findById(userId).get();
        User user = new User(userEntity);

        // when
        user.addRole(new Role(new UserId(userId), RoleEnum.ADMIN));
        UserEntity updated = userMapper.toUserEntity(user);
        userRepository.save(updated);
        UserEntity result = userRepository.findById(userId).get();

        // then
        Assertions.assertEquals(2,result.getRole().size());
        Assertions.assertEquals(RoleEnum.USER,result.getRole().get(0).getRoleName());
        Assertions.assertEquals(RoleEnum.ADMIN,result.getRole().get(1).getRoleName());
    }
}
