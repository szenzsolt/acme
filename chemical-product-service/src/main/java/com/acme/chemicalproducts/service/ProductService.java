package com.acme.chemicalproducts.service;

import com.acme.chemicalproducts.data.Product;
import com.acme.chemicalproducts.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product, BigDecimal fee) {
        product.setFee(fee);
        product.setPrice(calculatePrice(product));
        product.setDeliveryDate(LocalDate.now());
        return productRepository.save(product);
    }

    public void removeProduct(Long id) {
        productRepository.removeById(id);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    private BigDecimal calculatePrice(Product product){
        return product.getFee().multiply(product.getAmount());
    }

}
