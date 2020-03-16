package com.playground.loaylty.loaltyaxondemo.core.api.events;

import java.time.LocalDate;
import java.util.UUID;

public class CreateAccountEvent {
    private final UUID uuid;
    private final long initBalance;
    private final LocalDate localDate;

    public CreateAccountEvent(UUID uuid, long initBalance, LocalDate localDate) {
        this.uuid = uuid;
        this.initBalance = initBalance;
        this.localDate = localDate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public long getInitBalance() {
        return initBalance;
    }
}
