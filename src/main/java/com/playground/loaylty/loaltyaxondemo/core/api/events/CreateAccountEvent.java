package com.playground.loaylty.loaltyaxondemo.core.api.events;

import java.util.UUID;

public class CreateAccountEvent {
    private final UUID uuid;

    public CreateAccountEvent(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
