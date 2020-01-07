package com.acme.insurancefeeservice.controller;

import com.acme.insurancefeeservice.data.Insurance;
import com.acme.insurancefeeservice.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class InsuranceFeeController {

    @Autowired
    private FeeService feeService;

    @GetMapping("/insurance-fee/{category}/{amount}")
    public Insurance calculateInsurance(@PathVariable String category, @PathVariable BigDecimal amount){
        Insurance insuranceFee = feeService.getFee(category, amount);

        return insuranceFee;
    }
}
