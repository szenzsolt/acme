package com.acme.chemicalproducts.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("Product_Name")
    private String productName;

    @Builder.Default
    @JsonProperty("Category")
    private String category = "Generic";

    @NotNull(message = "Amount is mandatory")
    @Column(precision=15, scale=5)
    @JsonProperty("Amount")
    private BigDecimal amount;

    @Column(precision=15, scale=5)
    @JsonProperty("Price")
    private BigDecimal price;

    @Column(precision=15, scale=5)
    @JsonProperty("Fee")
    private BigDecimal fee;

    @JsonProperty("Delivery_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate deliveryDate;

    public Product(String productName, String category, BigDecimal amount) {
        this.productName = productName;
        this.category = category;
        this.amount = amount;
    }

}
