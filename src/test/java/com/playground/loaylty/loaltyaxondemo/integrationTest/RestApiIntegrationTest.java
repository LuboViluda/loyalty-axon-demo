package com.playground.loaylty.loaltyaxondemo.integrationTest;

import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

public class RestApiIntegrationTest {
    private final static String BASE_URL = "http://localhost:8080/accounts/";

    @Test
    public void createAccount() {
        RestTemplate restTemplate = new RestTemplate();

        String id = restTemplate.postForObject(BASE_URL + "createNewAccount", null, String.class);

        restTemplate.postForObject(BASE_URL + "/createTransaction/" + id + "/100", null, String.class);
    }
}
