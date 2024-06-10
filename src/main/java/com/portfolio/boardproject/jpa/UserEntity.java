package com.portfolio.boardproject.jpa;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @Column(name = "user_id")
    private UUID id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private Boolean enabled;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoleEntity> role;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public UserEntity(UUID id, String username, String password, String email, Boolean enabled, List<RoleEntity> role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
