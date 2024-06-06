package com.portfolio.boardproject.mapper;

import com.portfolio.boardproject.domain.Role;
import com.portfolio.boardproject.domain.User;
import com.portfolio.boardproject.jpa.RoleEntity;
import com.portfolio.boardproject.jpa.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toUserEntity(User user) {
        return UserEntity.builder()
                .id(user.getId().getValue())
                .username(user.getUsername())
                .password(user.getPassword().getValue())
                .email(user.getEmail())
                .enabled(user.getEnabled())
                .role(user.getRoles().stream().map(this::toRoleEntity).toList())
                .build();
    }


    private RoleEntity toRoleEntity(Role role) {
        return RoleEntity.builder()
                .user(UserEntity.builder().id(role.getUserId().getValue()).build())
                .roleName(role.getRole())
                .build();
    }
}
