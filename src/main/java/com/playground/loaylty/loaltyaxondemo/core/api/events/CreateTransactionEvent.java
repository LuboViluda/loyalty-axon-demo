package com.playground.loaylty.loaltyaxondemo.core.api.events;

import java.time.LocalDate;
import java.util.UUID;

public class CreateTransactionEvent {
    private final UUID accountUuid;
    private final LocalDate date;
    private final long pendingCredit;

    public CreateTransactionEvent(UUID accountUuid, LocalDate date, long amount) {
        this.accountUuid = accountUuid;
        this.date = date;
        this.pendingCredit = amount;
    }

    public UUID getAccountUuid() {
        return accountUuid;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getPendingCredit() {
        return pendingCredit;
    }
}
