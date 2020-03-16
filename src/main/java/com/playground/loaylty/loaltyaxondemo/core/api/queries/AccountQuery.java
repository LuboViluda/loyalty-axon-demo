package com.playground.loaylty.loaltyaxondemo.core.api.queries;

import java.util.UUID;

public abstract class AccountQuery {
    private final UUID uuid;

    public AccountQuery(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
