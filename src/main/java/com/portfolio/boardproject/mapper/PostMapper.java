package com.portfolio.boardproject.mapper;

import com.portfolio.boardproject.command.post.PostTrackQueryResponse;
import com.portfolio.boardproject.domain.Post;
import com.portfolio.boardproject.jpa.PostEntity;
import com.portfolio.boardproject.jpa.UserEntity;
import com.portfolio.boardproject.valueobject.UserId;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {


    public PostEntity toPostEntity(Post post) {

        return PostEntity.builder()
                .postId(post.getPostId().getValue())
                .user(toUserEntity(post.getUserId()))
                .title(post.getTitle().getValue())
                .contents(post.getContents().getValue())
                .build();
    }

    public PostTrackQueryResponse toPostTrackQueryResponse(PostEntity postEntity) {
        return PostTrackQueryResponse.builder()
                .postId(postEntity.getPostId())
                .contents(postEntity.getContents())
                .title(postEntity.getTitle())
                .poster(postEntity.getUser().getUsername())
                .createdAt(postEntity.getCreatedAt())
                .updatedAt(postEntity.getUpdatedAt())
                .userId(postEntity.getUser().getId())
                .build();
    }

    private UserEntity toUserEntity(UserId userId) {
        return UserEntity.builder().id(userId.getValue()).build();
    }
}
