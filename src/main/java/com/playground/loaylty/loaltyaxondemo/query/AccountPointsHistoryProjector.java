package com.playground.loaylty.loaltyaxondemo.query;

import com.playground.loaylty.loaltyaxondemo.core.api.events.AddCreditForAccountEvent;
import com.playground.loaylty.loaltyaxondemo.core.api.events.CreateAccountEvent;
import com.playground.loaylty.loaltyaxondemo.core.api.events.DiscardCreditForAccountEvent;
import com.playground.loaylty.loaltyaxondemo.core.api.events.UseCreditForAccountEvent;
import com.playground.loaylty.loaltyaxondemo.core.api.queries.AccountTransactionHistoryQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.playground.loaylty.loaltyaxondemo.query.PointChange.ChangeType.*;

@Component
public class AccountPointsHistoryProjector {
    private final Map<UUID, PointsChangeTracker> accounts = new HashMap<>();

    @EventHandler
    void on(CreateAccountEvent createAccountEvent) {
        PointsChangeTracker pointsChangeTracker = new PointsChangeTracker();
        pointsChangeTracker.addPointsChange(createAccountEvent.getLocalDate(), createAccountEvent.getInitBalance(), CREATED);
        accounts.put(createAccountEvent.getUuid(), pointsChangeTracker);
    }

    @EventHandler
    void on(UseCreditForAccountEvent useCreditForAccountEvent) {
        PointsChangeTracker pointsChangeTracker = accounts.get(useCreditForAccountEvent.getAccountUuid());
        pointsChangeTracker.addPointsChange(useCreditForAccountEvent.getDate(), (- useCreditForAccountEvent.getUsedCredit()), USED_CREDIT);
    }

    @EventHandler
    void on(AddCreditForAccountEvent addCreditForAccountEvent) {
        PointsChangeTracker pointsChangeTracker = accounts.get(addCreditForAccountEvent.getAccountUuid());
        pointsChangeTracker.addPointsChange(addCreditForAccountEvent.getDate(), addCreditForAccountEvent.getCredit(), ADD_CREDIT);
    }

    @EventHandler
    void on(DiscardCreditForAccountEvent discardCreditForAccountEvent) {
        PointsChangeTracker pointsChangeTracker = accounts.get(discardCreditForAccountEvent.getAccountUuid());
        pointsChangeTracker.addPointsChange(discardCreditForAccountEvent.getDate(), 0, DISCARD_CREDIT);
    }

    @QueryHandler
    PointsChangeTracker handle(AccountTransactionHistoryQuery accountTransactionHistoryQuery) {
        UUID uuid = accountTransactionHistoryQuery.getUuid();
        return accounts.get(uuid);
    }
}
