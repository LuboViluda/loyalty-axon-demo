package com.playground.loaylty.loaltyaxondemo.core.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public class EvaluateCreditForAccountCommand {
    @TargetAggregateIdentifier
    private final UUID accountUuid;

    public EvaluateCreditForAccountCommand(UUID accountUuid) {
        this.accountUuid = accountUuid;
    }
}
