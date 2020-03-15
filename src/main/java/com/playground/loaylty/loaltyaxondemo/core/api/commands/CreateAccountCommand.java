package com.playground.loaylty.loaltyaxondemo.core.api.commands;

import org.axonframework.commandhandling.RoutingKey;

import java.util.UUID;

public class CreateAccountCommand {
    @RoutingKey
    private final UUID uuid;

    public CreateAccountCommand(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
