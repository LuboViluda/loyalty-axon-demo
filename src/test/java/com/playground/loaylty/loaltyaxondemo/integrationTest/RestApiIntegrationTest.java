package com.playground.loaylty.loaltyaxondemo.integrationTest;

import com.playground.loaylty.loaltyaxondemo.query.PointChange;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import static com.playground.loaylty.loaltyaxondemo.endpoints.AccountController.AccountDto;
import static com.playground.loaylty.loaltyaxondemo.query.PointChange.ChangeType.CREATED;
import static com.playground.loaylty.loaltyaxondemo.query.PointChange.ChangeType.USED_CREDIT;
import static org.assertj.core.api.Assertions.assertThat;

public class RestApiIntegrationTest {
    private final static String BASE_URL = "http://localhost:8080/accounts/";
    private final long INIT_VALUE = 100L;

    @Test
    public void createAccountAndGetHistory() {
        RestTemplate restTemplate = new RestTemplate();

        String id = restTemplate.postForObject(BASE_URL + "createNewAccount", null, String.class);
        AccountDto accountDto = restTemplate.getForObject(BASE_URL + id + "/history", AccountDto.class);

        assertThat(accountDto.getPointChanges()).extracting(PointChange::getChange).containsExactly(INIT_VALUE);
        assertThat(accountDto.getPointChanges()).extracting(PointChange::getChangeType).containsExactly(CREATED);
    }

    @Test
    public void createAccount_useCredit_correctHistory() {
        RestTemplate restTemplate = new RestTemplate();
        String id = restTemplate.postForObject(BASE_URL + "createNewAccount", null, String.class);
        restTemplate.postForObject(BASE_URL + id + "/useCredit/50", null, String.class);

        AccountDto accountDto = restTemplate.getForObject(BASE_URL + id + "/history", AccountDto.class);

        assertThat(accountDto.getPointChanges()).extracting(PointChange::getChange).containsExactly(INIT_VALUE, -50L);
        assertThat(accountDto.getPointChanges()).extracting(PointChange::getChangeType).containsExactly(CREATED, USED_CREDIT);
    }

    @Test
    public void createAccount_getAvailableCredit() {
        RestTemplate restTemplate = new RestTemplate();
        String id = restTemplate.postForObject(BASE_URL + "createNewAccount", null, String.class);

        AccountDto accountDto = restTemplate.getForObject(BASE_URL + id, AccountDto.class);

        assertThat(accountDto.getAvailablePoints()).isEqualTo(100);
    }

    @Test
    public void createAccount_useCredit_getAvailableCredit() {
        RestTemplate restTemplate = new RestTemplate();

        String id = restTemplate.postForObject(BASE_URL + "createNewAccount", null, String.class);
        restTemplate.postForObject(BASE_URL + id + "/useCredit/50", null, String.class);
        AccountDto accountDto = restTemplate.getForObject(BASE_URL + id, AccountDto.class);

        assertThat(accountDto.getAvailablePoints()).isEqualTo(50);
    }

    @Test
    public void createAccount_createTransaction_getPendingPoints() {
        RestTemplate restTemplate = new RestTemplate();

        String id = restTemplate.postForObject(BASE_URL + "createNewAccount", null, String.class);
        restTemplate.postForObject(BASE_URL + id + "/createTransaction/50", null, String.class);
        AccountDto accountDto = restTemplate.getForObject(BASE_URL + id, AccountDto.class);

        assertThat(accountDto.getPendingPoints()).isEqualTo(50);
    }

//    todo other tests
}
