package com.playground.loaylty.loaltyaxondemo.core.api.events;

import java.time.LocalDate;
import java.util.UUID;

public class AddCreditForAccountEvent {
    private final UUID accountUuid;
    private final LocalDate date;
    private final long credit;

    public AddCreditForAccountEvent(UUID accountUuid, LocalDate date, long amount) {
        this.accountUuid = accountUuid;
        this.date = date;
        this.credit = amount;
    }

    public UUID getAccountUuid() {
        return accountUuid;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getCredit() {
        return credit;
    }
}
