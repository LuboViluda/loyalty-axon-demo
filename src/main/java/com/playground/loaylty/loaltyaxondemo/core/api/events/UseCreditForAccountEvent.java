package com.playground.loaylty.loaltyaxondemo.core.api.events;

import java.time.LocalDate;
import java.util.UUID;

public class UseCreditForAccountEvent {
    private final UUID accountUuid;
    private final LocalDate date;
    private final long usedCredit;

    public UseCreditForAccountEvent(UUID accountUuid, LocalDate date, long amount) {
        this.accountUuid = accountUuid;
        this.date = date;
        this.usedCredit = amount;
    }

    public UUID getAccountUuid() {
        return accountUuid;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getUsedCredit() {
        return usedCredit;
    }
}
