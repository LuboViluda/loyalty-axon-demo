package com.playground.loaylty.loaltyaxondemo.core.api.events;

import java.time.LocalDate;
import java.util.UUID;

public class DiscardCreditForAccountEvent {
    private final UUID accountUuid;
    private final LocalDate date;

    public DiscardCreditForAccountEvent(UUID accountUuid, LocalDate date) {
        this.accountUuid = accountUuid;
        this.date = date;
    }

    public UUID getAccountUuid() {
        return accountUuid;
    }

    public LocalDate getDate() {
        return date;
    }
}
