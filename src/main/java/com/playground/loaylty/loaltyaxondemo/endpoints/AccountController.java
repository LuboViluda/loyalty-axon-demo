package com.playground.loaylty.loaltyaxondemo.endpoints;

import com.playground.loaylty.loaltyaxondemo.core.api.commands.CreateAccountCommand;
import com.playground.loaylty.loaltyaxondemo.core.api.commands.CreateTransactionCommand;
import com.playground.loaylty.loaltyaxondemo.core.api.commands.EvaluateCreditForAccountCommand;
import com.playground.loaylty.loaltyaxondemo.core.api.commands.UseCreditForAccountCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/accounts")
@RestController
public class AccountController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    AccountController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/createNewAccount")
    public CompletableFuture<String> createAccount() {
        CompletableFuture<UUID> send = commandGateway.send(new CreateAccountCommand(UUID.randomUUID()));

        return send.thenApply((UUID s) -> s.toString().replace("\"", ""));
    }

    @PostMapping("/createTransaction/{accountId}/{amount}")
    public void createTransaction(@PathVariable("accountId") String accountId,
                                                       @PathVariable("amount") Long amount) {
        CompletableFuture<Object> send = commandGateway.send(new CreateTransactionCommand(UUID.fromString(accountId), LocalDate.now(), amount));
    }

    @PostMapping("/evaluateCredit/{accountId}")
    public void evaluateCredit(@PathVariable("accountId") String accountId) {
        commandGateway.send(new EvaluateCreditForAccountCommand(UUID.fromString(accountId)));
    }

    @PostMapping("/useCredit/{accountId}/{amount}")
    public void useCredit(@PathVariable("accountId") String accountId,
                          @PathVariable("amount") Long amount) {
        commandGateway.send(new UseCreditForAccountCommand(UUID.fromString(accountId), LocalDate.now(), amount));
    }
//    @GetMapping("/")
//    public CompletableFuture<List> getAccounts() {
//        return queryGateway.query(new AllAccountsViewQuery(), ResponseTypes.instanceOf(List.class));
//    }

//    @GetMapping("/{}")
//    public CompletableFuture<List> getAccounts() {
//        return queryGateway.query(new AllAccountsViewQuery(), ResponseTypes.instanceOf(List.class));
//    }

//    @GetMapping("/")
//    public CompletableFuture<AccountView> getAccount() throws ExecutionException, InterruptedException {
//        UUID uuid = UUID.randomUUID();
//
//        commandGateway.send(new CreateTransactionCommand(uuid, LocalDate.now(), 10));
//        commandGateway.send(new CreateTransactionCommand(uuid, LocalDate.now(), 20));
//
//        CompletableFuture<AccountView> query = queryGateway.query(new AccountViewQuery(uuid), ResponseTypes.instanceOf(AccountView.class));
//
//        AccountView accountView = query.get();
//
//        return query;
//    }

}
