package com.acme.insurancefeeservice.service;

import com.acme.insurancefeeservice.data.Insurance;
import com.acme.insurancefeeservice.data.InsuranceFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FeeService {

    @Autowired
    private InsuranceFeeRepository insuranceFeeRepository;

    public void saveFee(Insurance fee) {
        insuranceFeeRepository.save(fee);
    }

    public void saveAllFee(List<Insurance> fees) {
        fees.forEach(f-> saveFee(f));
    }

    public Insurance getFee(String category, BigDecimal amount) {
        return insuranceFeeRepository.findFeeByCategoryAndAmount(category, amount);
    }

}
