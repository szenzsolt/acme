package com.acme.insurancefeeservice.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface InsuranceFeeRepository extends JpaRepository<Insurance, Long> {

    @Query(value= "SELECT * FROM insurance_fee f WHERE f.category = :category AND ("
        + "(f.limit_Low <= :amount AND f.limit_Top>= :amount) OR"
        + "(f.limit_Low <= :amount AND f.limit_Top<= -1))",
        nativeQuery = true)
    Insurance findFeeByCategoryAndAmount(@Param("category") String category, @Param("amount") BigDecimal amount);

}
