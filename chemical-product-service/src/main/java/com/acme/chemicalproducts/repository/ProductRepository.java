package com.acme.chemicalproducts.repository;

import com.acme.chemicalproducts.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Long removeById(Long id);
}
