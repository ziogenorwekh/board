package com.portfolio.boardproject.domain;

import com.portfolio.boardproject.jpa.PostEntity;
import com.portfolio.boardproject.jpa.RoleEntity;
import com.portfolio.boardproject.jpa.UserEntity;
import com.portfolio.boardproject.valueobject.Password;
import com.portfolio.boardproject.valueobject.UserId;
import lombok.Getter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
public class User {

    private final UserId id;
    private Password password;
    private final String username;
    private final String email;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private List<Post> posts;
    private List<Role> roles;

    public User(UserEntity userEntity) {
        this.id = new UserId(userEntity.getId());
        this.password = new Password(userEntity.getPassword());
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.enabled = userEntity.getEnabled();
        this.roles = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.createdAt = userEntity.getCreatedAt();
        for (PostEntity postEntity : userEntity.getPosts()) {
            this.posts.add(new Post(postEntity));
        }
        for (RoleEntity roleEntity : userEntity.getRole()) {
            this.roles.add(new Role(this.id, roleEntity));
        }
    }

    public User(UUID userId, String password, String username, String email) {
        this.id = new UserId(userId);
        this.password = new Password(password);
        this.username = username;
        this.email = email;
        this.enabled = false;
        this.roles = new ArrayList<>();
        this.posts = new ArrayList<>();
    }


    public void updatePassword(Password newPassword) {
        this.password = newPassword;
    }

    public void addRole(Role role) {
        if (roles.contains(role)) {
            throw new IllegalArgumentException("Role already exists");
        }
        roles.add(role);
    }

    public void updateEnabled() {
        this.enabled = true;
    }


    @Override
    public String toString() {
        return "User{" +
                "createdAt=" + createdAt +
                ", enabled=" + enabled +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password=" + password +
                ", id=" + id +
                '}';
    }
}
