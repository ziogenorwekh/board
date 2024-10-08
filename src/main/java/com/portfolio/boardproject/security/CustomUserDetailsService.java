package com.portfolio.boardproject.security;

import com.portfolio.boardproject.domain.User;
import com.portfolio.boardproject.exception.UserNotFoundException;
import com.portfolio.boardproject.jpa.UserEntity;
import com.portfolio.boardproject.jpa.UserRepository;
import com.portfolio.boardproject.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public CustomUserDetailsService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        User user = new User(userEntity);
        log.info("user found email is : {}", user.getEmail());
        return new CustomUserDetails(user);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(String.format("User with email %s not found", email)));
        log.info("user found email is : {}", userEntity.getEmail());
        return new User(userEntity);
    }


    @Transactional
    public void activateUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(String.format("User with email %s not found", email)));
        User user = new User(userEntity);
        user.updateEnabled();
        UserEntity updatedUserEntity = userMapper.toUserEntity(user);
        log.info("activated user : {}", updatedUserEntity.getEmail());
        userRepository.save(updatedUserEntity);
    }



}
