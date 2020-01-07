package com.acme.chemicalproducts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.acme.chemicalproducts")
@EnableDiscoveryClient
public class ChemicalproductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChemicalproductsApplication.class, args);
    }

}
