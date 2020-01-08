package com.acme.chemicalproducts.controller;

import com.acme.chemicalproducts.ProductStorageProxy;
import com.acme.chemicalproducts.data.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductStorageControllerTest {
    @MockBean
    private ProductStorageProxy productStorageProxy;

    @LocalServerPort
    private int serverPort;

    private static final String ADD_PRODUCT_PATH = "/store-product/new";
    private static String baseUrl;
    private URI uri;
    private RestTemplate restTemplate;

    private BigDecimal fee;
    private BigDecimal amount;
    private BigDecimal expectedPrice;
    private Product product;

    @BeforeEach
    void init() {
        fee = new BigDecimal(0.01);
        amount = new BigDecimal(10000);
        expectedPrice = fee.multiply(amount);
        product = new Product("", "Toxic", amount);
    }

    @Test
    public void testAddNewProduct() throws URISyntaxException {
        Product productFeeResponse = new Product();
        productFeeResponse.setFee(fee);

        when(productStorageProxy.retrieveFeeValue(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(productFeeResponse);

        configureConnection(ADD_PRODUCT_PATH);
        ResponseEntity<Product> result = restTemplate.postForEntity(uri, product, Product.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getBody().getPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void testFeeIsNotAvailable() throws URISyntaxException {
        when(productStorageProxy.retrieveFeeValue(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(null);

        Exception exception = assertThrows(
            HttpClientErrorException.class,
            () -> {
                configureConnection(ADD_PRODUCT_PATH);

                ResponseEntity<Product> result = restTemplate.postForEntity(uri, product, Product.class);
                assertThat(result.getStatusCodeValue()).isEqualTo(HttpStatus.NOT_FOUND.value());
            });
        assertThat(exception.getMessage().contains("No fee found")).isTrue();
    }

    @Test
    public void testNewProductAmountIsNotValid() throws URISyntaxException {
        product.setAmount(null);
        configureConnection(ADD_PRODUCT_PATH);
        Exception exception = assertThrows(
            HttpClientErrorException.class,
            () -> {
                configureConnection(ADD_PRODUCT_PATH);

                ResponseEntity<Product> result = restTemplate.postForEntity(uri, product, Product.class);
                assertThat(result.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            });
        assertThat(exception.getMessage().contains("Validation Error")).isTrue();

    }

    private void configureConnection(String path) throws URISyntaxException {
        baseUrl = "http://localhost:" + serverPort + path;
        uri = new URI(baseUrl);
        restTemplate = new RestTemplate();
    }
}
