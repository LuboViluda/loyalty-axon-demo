package com.playground.loaylty.loaltyaxondemo.query;

import com.playground.loaylty.loaltyaxondemo.core.api.events.AddCreditForAccountEvent;
import com.playground.loaylty.loaltyaxondemo.core.api.events.CreateAccountEvent;
import com.playground.loaylty.loaltyaxondemo.core.api.events.CreateTransactionEvent;
import com.playground.loaylty.loaltyaxondemo.core.api.events.DiscardCreditForAccountEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class AccountPendingPointsProjector {
    private final Map<UUID, Long> accounts = new HashMap<>();

    @EventHandler
    void on(CreateAccountEvent createAccountEvent) {
        accounts.put(createAccountEvent.getUuid(), 0L);
    }

    @EventHandler
    void on(CreateTransactionEvent createTransactionEvent) {
        accounts.put(createTransactionEvent.getAccountUuid(), createTransactionEvent.getPendingCredit());
    }

    @EventHandler
    void on(AddCreditForAccountEvent addCreditForAccountEvent) {
        accounts.put(addCreditForAccountEvent.getAccountUuid(), 0L);
    }

    @EventHandler
    void on(DiscardCreditForAccountEvent discardCreditForAccountEvent) {
        accounts.put(discardCreditForAccountEvent.getAccountUuid(), 0L);
    }
}
