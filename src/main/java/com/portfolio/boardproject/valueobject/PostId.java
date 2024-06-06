package com.portfolio.boardproject.valueobject;

import java.util.UUID;

public class PostId extends BaseId<UUID> {

    public PostId(UUID value) {
        super(value);
    }
}
