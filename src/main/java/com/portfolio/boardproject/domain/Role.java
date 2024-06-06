package com.portfolio.boardproject.domain;

import com.portfolio.boardproject.jpa.RoleEntity;
import com.portfolio.boardproject.jpa.RoleEnum;
import com.portfolio.boardproject.valueobject.UserId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Role {

    private final UserId userId;
    private final RoleEnum role;

    public Role(UserId userId, RoleEntity roleEntity) {
        this.userId = userId;
        this.role = roleEntity.getRoleName();
    }

    public Role(UserId userId, RoleEnum role) {
        this.userId = userId;
        this.role = role;
    }
}
