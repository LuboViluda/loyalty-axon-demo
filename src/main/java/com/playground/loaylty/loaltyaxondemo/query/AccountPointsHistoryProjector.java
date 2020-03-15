package com.playground.loaylty.loaltyaxondemo.query;

import com.playground.loaylty.loaltyaxondemo.core.api.events.AddCreditForAccountEvent;
import com.playground.loaylty.loaltyaxondemo.core.api.events.CreateAccountEvent;
import com.playground.loaylty.loaltyaxondemo.core.api.events.DiscardCreditForAccountEvent;
import com.playground.loaylty.loaltyaxondemo.core.api.events.UseCreditForAccountEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.playground.loaylty.loaltyaxondemo.query.PointsChangeTracker.ChangeType.*;

@Component
public class AccountPointsHistoryProjector {
    private final Map<UUID, PointsChangeTracker> accounts = new HashMap<>();

    @EventHandler
    void on(CreateAccountEvent createAccountEvent) {
        accounts.put(createAccountEvent.getUuid(), new PointsChangeTracker());
    }

    @EventHandler
    void on(UseCreditForAccountEvent useCreditForAccountEvent) {
        PointsChangeTracker pointsChangeTracker = accounts.get(useCreditForAccountEvent.getAccountUuid());
        pointsChangeTracker.addPointsChange(useCreditForAccountEvent.getDate(), Long.reverse(useCreditForAccountEvent.getUsedCredit()), USED_CREDIT);
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
}
