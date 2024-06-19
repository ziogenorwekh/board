package com.portfolio.boardproject.service;

import com.portfolio.boardproject.command.user.*;
import com.portfolio.boardproject.domain.Role;
import com.portfolio.boardproject.domain.User;
import com.portfolio.boardproject.exception.UserEmailDuplicatedException;
import com.portfolio.boardproject.exception.UserNotFoundException;
import com.portfolio.boardproject.jpa.RoleEnum;
import com.portfolio.boardproject.jpa.UserEntity;
import com.portfolio.boardproject.jpa.UserRepository;
import com.portfolio.boardproject.mapper.UserMapper;
import com.portfolio.boardproject.valueobject.Password;
import com.portfolio.boardproject.valueobject.UserId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserCreateResponse createUser(UserCreateCommand userCreateCommand) {
        validateDuplicatedEmail(userCreateCommand.getEmail());
        String encodePassword = passwordEncoder.encode(userCreateCommand.getPassword());
        UUID userId = UUID.randomUUID();
        User user = new User(userId, encodePassword
                , userCreateCommand.getUsername(), userCreateCommand.getEmail());
        Role role = new Role(new UserId(userId), RoleEnum.USER);
        user.addRole(role);

        UserEntity userEntity = userMapper.toUserEntity(user);
        UserEntity saved = userRepository.save(userEntity);
        return new UserCreateResponse(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getCreatedAt());
    }

    @Override
    @Transactional(readOnly = true)
    public UserTrackQueryResponse findUserById(UserTrackQuery userTrackQuery) {

        UserEntity userEntity = userRepository.findById(userTrackQuery.getUserId()).orElseThrow(() ->
                new UserNotFoundException(String.format("User with id %s not found", userTrackQuery.getUserId())));
        User user = new User(userEntity);
        return userMapper.toUserTrackQueryResponse(user);
    }

    @Override
    @Transactional
    public void updateUser(UserUpdateCommand userUpdateCommand) {
        UserEntity userEntity = userRepository.findById(userUpdateCommand.getUserId()).orElseThrow(() ->
                new UserNotFoundException(String.format("User with id %s not found",
                        userUpdateCommand.getUserId())));
        User user = new User(userEntity);

        if (!passwordEncoder.matches(userUpdateCommand.getCurrentPassword(), user.getPassword().getValue())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        Password newPassword = new Password(passwordEncoder.encode(userUpdateCommand.getNewPassword()));
        user.updatePassword(newPassword);
        UserEntity updatedEntity = userMapper.toUserEntity(user);
        userRepository.save(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteUser(UserDeleteCommand userDeleteCommand) {
        UserEntity userEntity = userRepository.findById(userDeleteCommand.getUserId())
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found",
                        userDeleteCommand.getUserId())));
        userRepository.delete(userEntity);
    }

    private void validateDuplicatedEmail(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new UserEmailDuplicatedException("User with email " + email + " already exists");
        });
    }
}
