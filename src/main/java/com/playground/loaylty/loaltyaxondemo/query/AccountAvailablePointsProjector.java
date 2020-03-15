package com.playground.loaylty.loaltyaxondemo.query;

import com.playground.loaylty.loaltyaxondemo.core.api.events.AddCreditForAccountEvent;
import com.playground.loaylty.loaltyaxondemo.core.api.events.CreateAccountEvent;
import com.playground.loaylty.loaltyaxondemo.core.api.events.DiscardCreditForAccountEvent;
import com.playground.loaylty.loaltyaxondemo.core.api.events.UseCreditForAccountEvent;
import com.playground.loaylty.loaltyaxondemo.core.api.queries.AccountAvailablePointsQuery;
import com.playground.loaylty.loaltyaxondemo.core.api.queries.AllAccountsViewQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AccountAvailablePointsProjector {
    private final Map<UUID, Long> accounts = new HashMap<>();

    @EventHandler
    void on(CreateAccountEvent createAccountEvent) {
        accounts.put(createAccountEvent.getUuid(), 0L);
    }

    @EventHandler
    void on(AddCreditForAccountEvent addCreditForAccountEvent) {
        accounts.put(addCreditForAccountEvent.getAccountUuid(), addCreditForAccountEvent.getCredit());
    }

    @EventHandler
    void on(UseCreditForAccountEvent useCreditForAccountEvent) {
        accounts.compute(useCreditForAccountEvent.getAccountUuid(), (k, v) -> v - useCreditForAccountEvent.getUsedCredit());
    }

    @EventHandler
    void on(DiscardCreditForAccountEvent discardCreditForAccountEvent) {
        accounts.put(discardCreditForAccountEvent.getAccountUuid(), 0L);
    }

    @QueryHandler
    public long handle(AccountAvailablePointsQuery accountViewQuery) {
        UUID requestedUuid = accountViewQuery.getUuid();
        if (!accounts.containsKey(requestedUuid)) {
            throw new IllegalArgumentException("No existing account");
        }
        return accounts.get(requestedUuid);
    }

    @QueryHandler
    public List<UUID> handle(AllAccountsViewQuery allAccountsViewQuery) {
        return Collections.unmodifiableList(new ArrayList<>(accounts.keySet()));
    }

}
