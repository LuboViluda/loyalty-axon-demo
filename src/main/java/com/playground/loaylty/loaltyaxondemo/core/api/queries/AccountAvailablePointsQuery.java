package com.playground.loaylty.loaltyaxondemo.core.api.queries;

import java.util.UUID;

public class AccountAvailablePointsQuery {
    private final UUID uuid;

    public AccountAvailablePointsQuery(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
