package com.playground.loaylty.loaltyaxondemo.aggregates;

import org.axonframework.modelling.command.EntityId;

import java.time.LocalDate;
import java.util.UUID;

public class Transaction {
    @EntityId
    private final UUID transactionUuid;
    private final LocalDate localDate;
    private final long amount;

    Transaction(UUID transactionUuid, LocalDate localDate, long amount) {
        this.transactionUuid = transactionUuid;
        this.localDate = localDate;
        this.amount = amount;
    }

    public UUID getTransactionUuid() {
        return transactionUuid;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public long getAmount() {
        return amount;
    }
}
