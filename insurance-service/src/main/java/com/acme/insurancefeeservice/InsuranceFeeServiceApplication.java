package com.acme.insurancefeeservice;

import com.acme.insurancefeeservice.data.InsuranceFeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InsuranceFeeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsuranceFeeServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(InsuranceFeeRepository repo) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
            }
        };
    }

}
