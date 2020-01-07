package com.acme.chemicalproducts;

import com.acme.chemicalproducts.data.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name ="insurance-service")
public interface  ProductStorageProxy {
    @GetMapping("/insurance-fee/{category}/{amount}")
    public Product retrieveFeeValue
        (@PathVariable("category") String category, @PathVariable("amount") BigDecimal amount);

}
