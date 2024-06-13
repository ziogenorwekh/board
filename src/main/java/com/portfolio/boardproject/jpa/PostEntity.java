package com.portfolio.boardproject.jpa;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PostEntity extends BaseEntity {

    @Id
    private UUID postId;

    private String title;

    private String contents;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public PostEntity(UUID postId, String title, String contents,
                      UserEntity user) {
        this.postId = postId;
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostEntity that = (PostEntity) o;
        return Objects.equals(postId, that.postId) && Objects.equals(title, that.title) && Objects.equals(contents, that.contents) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, title, contents, user);
    }
}
