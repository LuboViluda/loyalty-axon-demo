package com.playground.loaylty.loaltyaxondemo.endpoints;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.playground.loaylty.loaltyaxondemo.core.api.commands.CreateAccountCommand;
import com.playground.loaylty.loaltyaxondemo.core.api.commands.CreateTransactionCommand;
import com.playground.loaylty.loaltyaxondemo.core.api.commands.EvaluateCreditForAccountCommand;
import com.playground.loaylty.loaltyaxondemo.core.api.commands.UseCreditForAccountCommand;
import com.playground.loaylty.loaltyaxondemo.core.api.queries.AccountAvailablePointsQuery;
import com.playground.loaylty.loaltyaxondemo.core.api.queries.AccountPendingPointsQuery;
import com.playground.loaylty.loaltyaxondemo.core.api.queries.AccountTransactionHistoryQuery;
import com.playground.loaylty.loaltyaxondemo.core.api.queries.AllAccountsViewQuery;
import com.playground.loaylty.loaltyaxondemo.query.PointChange;
import com.playground.loaylty.loaltyaxondemo.query.PointsChangeTracker;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

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

    @PostMapping("/{accountId}/createTransaction/{amount}")
    public void createTransaction(@PathVariable("accountId") String accountId,
                                  @PathVariable("amount") Long amount) {
        CompletableFuture<Object> send = commandGateway.send(new CreateTransactionCommand(UUID.fromString(accountId), LocalDate.now(), amount));
    }

    @PostMapping("/{accountId}/evaluateCredit/")
    public void evaluateCredit(@PathVariable("accountId") String accountId) {
        commandGateway.send(new EvaluateCreditForAccountCommand(UUID.fromString(accountId)));
    }

    @PostMapping("/{accountId}/useCredit/{amount}")
    public void useCredit(@PathVariable("accountId") String accountId,
                          @PathVariable("amount") Long amount) {
        commandGateway.send(new UseCreditForAccountCommand(UUID.fromString(accountId), LocalDate.now(), amount));
    }

    @GetMapping("/")
    public CompletableFuture<List> getAccounts() {
        return queryGateway.query(new AllAccountsViewQuery(), ResponseTypes.instanceOf(List.class));
    }

    @GetMapping("/{accountId}")
    public CompletableFuture<AccountDto> getAccount(@PathVariable("accountId") String accountId) {
        UUID uuid = UUID.fromString(accountId);
        CompletableFuture<Long> pendingPoints = queryGateway.query(new AccountPendingPointsQuery(uuid), ResponseTypes.instanceOf(Long.class));
        CompletableFuture<Long> availablePoints = queryGateway.query(new AccountAvailablePointsQuery(uuid), ResponseTypes.instanceOf(Long.class));

        return pendingPoints.thenCombine(availablePoints, (p, a) -> new AccountDto(uuid.toString(), a, p));
    }

    @GetMapping("/{accountId}/history")
    public CompletableFuture<AccountDto> getAccountHistory(@PathVariable("accountId") String accountId) {
        UUID uuid = UUID.fromString(accountId);
        CompletableFuture<PointsChangeTracker> query =
                queryGateway.query(new AccountTransactionHistoryQuery(uuid), ResponseTypes.instanceOf(PointsChangeTracker.class));

        return query.thenApply(l -> {
            AccountDto accountDto = new AccountDto(accountId);
            accountDto.setPointChanges(l.getPointChangeList());
            return accountDto;
        });
    }

    @JsonInclude(NON_NULL)
    public static class AccountDto {
        private String uuid;
        private Long availablePoints;
        private Long pendingPoints;
        private List<PointChange> pointChanges;

        public AccountDto() {

        }
        public AccountDto(String uuid) {
            this.uuid = uuid;
        }

        public AccountDto(String uuid, long availablePoints, long pendingPoints) {
            this.uuid = uuid;
            this.availablePoints = availablePoints;
            this.pendingPoints = pendingPoints;
        }

        public String getUuid() {
            return uuid;
        }

        public Long getAvailablePoints() {
            return availablePoints;
        }

        public Long getPendingPoints() {
            return pendingPoints;
        }

        public List<PointChange> getPointChanges() {
            return pointChanges;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public void setAvailablePoints(Long availablePoints) {
            this.availablePoints = availablePoints;
        }

        public void setPendingPoints(Long pendingPoints) {
            this.pendingPoints = pendingPoints;
        }

        public void setPointChanges(List<PointChange> pointChanges) {
            this.pointChanges = pointChanges;
        }
    }
}
