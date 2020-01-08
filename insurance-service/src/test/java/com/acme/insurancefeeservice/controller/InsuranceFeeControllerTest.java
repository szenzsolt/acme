package com.acme.insurancefeeservice.controller;

import com.acme.insurancefeeservice.data.Insurance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InsuranceFeeControllerTest {

    @LocalServerPort
    private int serverPort;

    private static final String GET_FEE_PATH = "/insurance-fee/";
    private static String baseUrl;
    private URI uri;
    private RestTemplate restTemplate;

    private static final String INSERT_FEE_SQL = "sql/init.sql";


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/" + INSERT_FEE_SQL)
    public void testGetFee() throws URISyntaxException {
        String category = "TestCategory";
        BigDecimal amount = new BigDecimal(50);
        BigDecimal expectedFee = new BigDecimal("0.00100");

        configureConnection(createPathForFee(GET_FEE_PATH, category, amount));
        ResponseEntity<Insurance> result = restTemplate.getForEntity(uri, Insurance.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getBody().getFee()).isEqualTo(expectedFee);
    }

    @Test
    public void testGetFeeNotFound() throws URISyntaxException {
        String category = "TestCategory";
        BigDecimal amount = new BigDecimal(50);

        configureConnection(createPathForFee(GET_FEE_PATH, category, amount));
        ResponseEntity<Insurance> result = restTemplate.getForEntity(uri, Insurance.class);
        assertThat(result.getBody()).isNull();
    }

    private void configureConnection(String path) throws URISyntaxException {
        baseUrl = "http://localhost:" + serverPort + path;
        uri = new URI(baseUrl);
        restTemplate = new RestTemplate();
    }

    private String createPathForFee(String path, String category, BigDecimal amount) {
        StringBuilder feePath = new StringBuilder(path);
        feePath.append(category);
        feePath.append("/");
        feePath.append(amount);
        return feePath.toString();
    }


}
