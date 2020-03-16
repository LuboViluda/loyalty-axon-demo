package com.playground.loaylty.loaltyaxondemo;

import com.playground.loaylty.loaltyaxondemo.core.api.commands.EvaluateCreditForAccountCommand;
import com.playground.loaylty.loaltyaxondemo.core.api.queries.AllAccountsViewQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class LoyaltyServiceSchedule {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    LoyaltyServiceSchedule(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @Scheduled(cron = "17 17 23 ? * SUN")
    public void cronJobSch() {
       queryGateway.query(new AllAccountsViewQuery(), ResponseTypes.instanceOf(List.class))
               .thenAccept(usersIds -> {
                   for (Object uuid  : usersIds) {
                       commandGateway.send(new EvaluateCreditForAccountCommand((UUID) uuid));
                   }});
    }
}
