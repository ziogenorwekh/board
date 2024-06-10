package com.portfolio.boardproject.usertest;

import com.portfolio.boardproject.command.*;
import com.portfolio.boardproject.domain.Role;
import com.portfolio.boardproject.domain.User;
import com.portfolio.boardproject.jpa.RoleEntity;
import com.portfolio.boardproject.jpa.RoleEnum;
import com.portfolio.boardproject.jpa.UserEntity;
import com.portfolio.boardproject.jpa.UserRepository;
import com.portfolio.boardproject.mapper.UserMapper;
import com.portfolio.boardproject.service.UserService;
import com.portfolio.boardproject.service.UserServiceImpl;
import com.portfolio.boardproject.valueobject.UserId;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2,
//        replace = AutoConfigureTestDatabase.Replace.ANY)
//@EnableJpaAuditing
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private final UUID userId = UUID.randomUUID();
    private final LocalDateTime createdAt = LocalDateTime.now();

    @BeforeEach
    public void setUp() {
    }


    @Test
    @DisplayName("유저 생성")
    public void createUserTest() {
        UserCreateCommand userCreateCommand = Mockito.mock(UserCreateCommand.class);
        Mockito.when(userCreateCommand.getEmail()).thenReturn("test@example.com");
        Mockito.when(userCreateCommand.getPassword()).thenReturn("password");
        Mockito.when(userCreateCommand.getUsername()).thenReturn("testuser");

        UserEntity mockUserEntity = Mockito.mock(UserEntity.class);
        Mockito.when(userMapper.toUserEntity(Mockito.any(User.class))).thenReturn(mockUserEntity);

        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(UserEntity.builder()
                .id(userId)
                .createdAt(createdAt)
                .email("test@example.com")
                .username("testuser")
                .password("password").build());
        // when
        UserCreateResponse response = userService.createUser(userCreateCommand);

        // then
        Assertions.assertNotNull(response);
        Assertions.assertEquals("testuser", response.getUsername());
        Assertions.assertEquals("test@example.com", response.getEmail());
        Assertions.assertEquals(createdAt, response.getCreatedAt());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserEntity.class));
        Mockito.verify(userMapper, Mockito.times(1)).toUserEntity(Mockito.any(User.class));
    }

    @Test
    @DisplayName("유저 조회")
    public void userUpdateTest() {
        // given
        UserTrackQuery userTrackQuery = new UserTrackQuery(userId);
        UserEntity mockUserEntity = Mockito.mock(UserEntity.class);

        Mockito.when(userRepository.findById(Mockito.eq(userId))).thenReturn(Optional.of(mockUserEntity));
        Mockito.when(userMapper.toUserTrackQueryResponse(Mockito.any(User.class)))
                .thenReturn(UserTrackQueryResponse.builder()
                        .userId(userId)
                        .username("testuser")
                        .email("testuser@example.com")
                        .createdAt(LocalDateTime.now())
                        .build());

        // when
        UserTrackQueryResponse response = userService.findUserById(userTrackQuery);

        // then
        Assertions.assertNotNull(response);
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.eq(userId));
        Mockito.verify(userMapper, Mockito.times(1)).toUserTrackQueryResponse(Mockito.any(User.class));
    }

    @Test
    @DisplayName("유저 비밀번호 업데이트")
    public void userUpdateTests() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .enabled(true)
                .username("username")
                .password("password")
                .id(userId)
                .role(new ArrayList<>())
                .build();

        RoleEntity roleEntity = RoleEntity.builder().roleName(RoleEnum.USER).user(userEntity).build();
        userEntity.getRole().add(roleEntity);

        UserUpdateCommand userUpdateCommand = new UserUpdateCommand(userId, "password", "newpassword");
        Mockito.when(userRepository.findById(Mockito.eq(userId))).thenReturn(Optional.of(userEntity));

        UserEntity updatedEntity = UserEntity.builder()
                .enabled(true)
                .username("username")
                .password("newpassword")
                .id(userId)
                .role(new ArrayList<>())
                .build();
        Mockito.when(userMapper.toUserEntity(Mockito.any(User.class))).thenReturn(updatedEntity);

        // when
        userService.updateUser(userUpdateCommand);

        // then
        Mockito.verify(userMapper, Mockito.times(1)).toUserEntity(Mockito.any(User.class));

        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.eq(userId));
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserEntity.class));

        // 캡처된 UserEntity를 검증
        ArgumentCaptor<UserEntity> userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);
        Mockito.verify(userRepository).save(userEntityCaptor.capture());
        UserEntity capturedUserEntity = userEntityCaptor.getValue();

        // 새로운 비밀번호로 업데이트되었는지 확인
        Assertions.assertEquals("newpassword", capturedUserEntity.getPassword());
    }

    @Test
    @DisplayName("유저 삭제")
    public void userDeleteTests() {

        // given
        UserDeleteCommand userDeleteCommand = new UserDeleteCommand(userId);

        UserEntity mockUserEntity = Mockito.mock(UserEntity.class);
        Mockito.when(userRepository.findById(Mockito.eq(userId))).thenReturn(Optional.of(mockUserEntity));
        Mockito.doNothing().when(userRepository).delete(mockUserEntity);

        // when
        userService.deleteUser(userDeleteCommand);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.eq(userId));
        Mockito.verify(userRepository, Mockito.times(1)).delete(Mockito.eq(mockUserEntity));
    }
}
