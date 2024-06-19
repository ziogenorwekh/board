package com.portfolio.boardproject.security;

import com.portfolio.boardproject.domain.User;
import com.portfolio.boardproject.jpa.RoleEntity;
import com.portfolio.boardproject.jpa.RoleEnum;
import com.portfolio.boardproject.jpa.UserEntity;
import com.portfolio.boardproject.jpa.UserRepository;
import com.portfolio.boardproject.vo.LoginResponseVO;
import com.portfolio.boardproject.vo.LoginVO;
import com.portfolio.boardproject.vo.VerifyCodeVO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootTest
//@EnableJpaAuditing
@ActiveProfiles("test")
public class SecurityServiceTest {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private User user;

    @BeforeEach
    public void setUp() {
        String encodePassword = passwordEncoder.encode("password");
        UUID userId = UUID.randomUUID();

        UserEntity userEntity = UserEntity.builder()
                .posts(new ArrayList<>())
                .role(new ArrayList<>())
                .id(userId)
                .username("username")
                .password(encodePassword)
                .email("email@email.com")
                .enabled(true)
                .build();
        RoleEntity roleEntity = RoleEntity.builder().user(userEntity).roleName(RoleEnum.USER).build();
        userEntity.getRole().add(roleEntity);
        userRepository.save(userEntity);

        UUID userId2 = UUID.randomUUID();
        String encodePassword2 = passwordEncoder.encode("otherpwd");
        UserEntity userEntity2 = UserEntity.builder()
                .posts(new ArrayList<>())
                .role(new ArrayList<>())
                .id(userId2)
                .username("newuser")
                .password(encodePassword2)
                .email("ziogenorwekh@gmail.com")
                .enabled(false)
                .build();
        RoleEntity roleEntity2 = RoleEntity.builder().user(userEntity2).roleName(RoleEnum.USER).build();
        userEntity.getRole().add(roleEntity2);

        userRepository.save(userEntity2);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    @Test
    @DisplayName("로그인 메서드 테스트")
    public void userLogin() {
        // given
        LoginVO loginVO = new LoginVO("username", "password");

        // when
        LoginResponseVO loginResponseVO = authService.login(loginVO);

        // then
        Assertions.assertNotNull(loginResponseVO);
    }

    @Test
    @DisplayName("메일 보내기 테스트")
    public void mailSendTests() {
        // given
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "password", "username", "ziogenorwekh@gmail.com");
        // when
        authService.sendMail(user);
        // then
        Set<String> keys = redisTemplate.keys("*");
        Assertions.assertEquals(1, keys.size());
        Assertions.assertTrue(redisTemplate.hasKey("ziogenorwekh@gmail.com").booleanValue());
    }

    @Test
    @DisplayName("이메일로부터 온 값을 받아 유저 인증하기")
    public void verifyCodeUserActivateTest() throws ExecutionException, InterruptedException {
        // given
        User user = customUserDetailsService.findByEmail("ziogenorwekh@gmail.com");
        // when
        Future<String> stringFuture = authService.sendMail(user);
        // then
        Boolean aBoolean = authService.verifyEmail(new VerifyCodeVO("ziogenorwekh@gmail.com",stringFuture.get()));
        Assertions.assertTrue(aBoolean);
    }

    @Test
    @DisplayName("이메일 인증 받은 값을 유저 디테일 서비스에 적용하고 최종 확인하기")
//    @Transactional
    public void verifyCodeUserActivateWithDetailsServiceTest() throws ExecutionException, InterruptedException {
        // given
        User user = customUserDetailsService.findByEmail("ziogenorwekh@gmail.com");
        Future<String> code = authService.sendMail(user);
        authService.verifyEmail(new VerifyCodeVO(user.getEmail(), code.get()));

        // when
        customUserDetailsService.activateUser("ziogenorwekh@gmail.com");
        // then
        UserEntity result = userRepository.findByEmail("ziogenorwekh@gmail.com").get();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getEnabled());
    }
}
