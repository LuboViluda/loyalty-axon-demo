package com.playground.loaylty.loaltyaxondemo.core.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;
import java.util.UUID;

public class CreateTransactionCommand {
    @TargetAggregateIdentifier
    private final UUID accountUuid;
    private final LocalDate date;
    private final long amount;

    public CreateTransactionCommand(UUID accountUuid, LocalDate date, long amount) {
        this.accountUuid = accountUuid;
        this.date = date;
        this.amount = amount;
    }

    public UUID getAccountUuid() {
        return accountUuid;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getAmount() {
        return amount;
    }
}
