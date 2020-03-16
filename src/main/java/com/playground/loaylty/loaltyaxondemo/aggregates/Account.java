package com.playground.loaylty.loaltyaxondemo.aggregates;

import com.playground.loaylty.loaltyaxondemo.core.api.commands.CreateAccountCommand;
import com.playground.loaylty.loaltyaxondemo.core.api.commands.CreateTransactionCommand;
import com.playground.loaylty.loaltyaxondemo.core.api.commands.EvaluateCreditForAccountCommand;
import com.playground.loaylty.loaltyaxondemo.core.api.events.*;
import com.playground.loaylty.loaltyaxondemo.core.api.exceptions.InsufficientFundException;
import com.playground.loaylty.loaltyaxondemo.util.DateUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Aggregate
public class Account {
    private static final int START_AVAILABLE_POINTS = 100;

    @AggregateIdentifier
    private UUID accountUuid;
    @AggregateMember
    List<Transaction> transactions;
    private long availablePoints;

    public Account() {
        // for Axon
    }

    @CommandHandler
    public Account(CreateAccountCommand createAccountCommand) {
        AggregateLifecycle.apply(new CreateAccountEvent(createAccountCommand.getUuid(), START_AVAILABLE_POINTS, DateUtils.now()));
    }

    @CommandHandler
    public void addTransaction(CreateTransactionCommand createTransactionCommand) {
        AggregateLifecycle.apply(
                new CreateTransactionEvent(
                        accountUuid,
                        createTransactionCommand.getDate(),
                        getBonusPoints(createTransactionCommand.getAmount())));
    }

    @CommandHandler
    public void useCreditForAccount(UseCreditForAccountEvent useCreditForAccountEvent) {
        if (useCreditForAccountEvent.getUsedCredit() > availablePoints) {
            throw new InsufficientFundException();
        }

        AggregateLifecycle.apply(
                new UseCreditForAccountEvent(
                        accountUuid,
                        useCreditForAccountEvent.getDate(),
                        useCreditForAccountEvent.getUsedCredit()));
    }

    // schedule command handler?
    @CommandHandler
    public void evaluateCreditForAccount(EvaluateCreditForAccountCommand evaluateCreditForAccountCommand) {
        if (wasInactiveInLast5Weeks()) {
            fireDiscardCreditEvent();
        }

        updateCreditPoints();
    }

    @EventSourcingHandler
    public void on(CreateAccountEvent createAccountEvent) {
        accountUuid = createAccountEvent.getUuid();
        transactions = new ArrayList<>();
        availablePoints = START_AVAILABLE_POINTS;
    }

    @EventSourcingHandler
    public void on(CreateTransactionEvent createTransactionEvent) {
        transactions.add(new Transaction(UUID.randomUUID(), createTransactionEvent.getDate(), createTransactionEvent.getPendingCredit()));
    }

    @EventSourcingHandler
    public void on(UseCreditForAccountEvent useCreditForAccountEvent) {
        availablePoints -= useCreditForAccountEvent.getUsedCredit();
    }

    @EventSourcingHandler
    public void on(DiscardCreditForAccountEvent discardCreditForAccountEvent) {
        availablePoints = 0;
    }

    @EventSourcingHandler
    public void on(AddCreditForAccountEvent addCreditForAccountEvent) {
        availablePoints += addCreditForAccountEvent.getCredit();
    }

    private long getBonusPoints(long transactionAmount) {
        long points = 0;
        if (transactionAmount > 7500) {
            long above = transactionAmount - 7500;
            points += above * 3;
            transactionAmount -= above;
        }
        if (transactionAmount > 5000) {
            long above = transactionAmount - 5000;
            points += above * 2;
            transactionAmount -= above;
        }
        return points + transactionAmount;
    }

    private boolean wasInactiveInLast5Weeks() {
        return transactions.stream()
                .noneMatch(t -> t.getLocalDate().isAfter(DateUtils.now().minusWeeks(5)));
    }

    private void fireDiscardCreditEvent() {
        AggregateLifecycle.apply(
                new DiscardCreditForAccountEvent(
                        accountUuid,
                        LocalDate.now()));
    }

    private void updateCreditPoints() {
        long credit = 0;
        Set<DayOfWeek> days = new HashSet<>();
        for (Transaction transaction : transactions) {
            credit += transaction.getAmount();
            days.add(transaction.getLocalDate().getDayOfWeek());
        }

        if (days.size() == 7 && credit >= 500) {
            AggregateLifecycle.apply(
                    new AddCreditForAccountEvent(
                            accountUuid,
                            DateUtils.now(),
                            credit));
        }
    }
}
