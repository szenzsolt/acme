package com.acme.chemicalproducts.controller;

import com.acme.chemicalproducts.data.Product;
import com.acme.chemicalproducts.ProductStorageProxy;
import com.acme.chemicalproducts.error.FeeNotFoundException;
import com.acme.chemicalproducts.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("store-product")
public class ProductStorageController {

    @Autowired
    private ProductStorageProxy productStorageProxy;

    @Autowired
    private ProductService productService;

    @PostMapping("/new")
    public Product addNewProduct(@Valid @RequestBody Product product) {
        Product insurancePrice = productStorageProxy.retrieveFeeValue(product.getCategory(), product.getAmount());
        if (Objects.nonNull(insurancePrice)) {
            return productService.addProduct(product, insurancePrice.getFee());
        } else {
            throw new FeeNotFoundException(product.getProductName(), product.getCategory());
        }
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public void deleteProduct(@PathVariable("id")Long id) {
        productService.removeProduct(id);
    }
}
