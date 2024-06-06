package com.portfolio.boardproject.domain;

import com.portfolio.boardproject.jpa.RoleEntity;
import com.portfolio.boardproject.jpa.UserEntity;
import com.portfolio.boardproject.valueobject.Password;
import com.portfolio.boardproject.valueobject.UserId;
import lombok.Getter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
public class User {

    private final UserId id;
    private Password password;
    private final String username;
    private final String email;
    private final Boolean enabled;
    private List<Role> roles;

    public User(UserEntity userEntity) {
        this.id = new UserId(userEntity.getId());
        this.password = new Password(userEntity.getPassword());
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.enabled = userEntity.getEnabled();
        this.roles = new ArrayList<>();
        for (RoleEntity roleEntity : userEntity.getRole()) {
            this.roles.add(new Role(this.id, roleEntity));
        }
    }

    public User(UUID id, String password, String username, String email) {
        this.id = new UserId(id);
        this.password = new Password(password);
        this.username = username;
        this.email = email;
        this.enabled = false;
        this.roles = new ArrayList<>();
    }


    public void updatePassword(Password currentPassword, Password newPassword) {
        if (password.equals(currentPassword)) {
            this.password = newPassword;
        } else {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }

    public void addRole(Role role) {
        if (roles.contains(role)) {
            throw new IllegalArgumentException("Role already exists");
        }
        roles.add(role);
    }

}
