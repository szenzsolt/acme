package com.acme.insurancefeeservice.data;

import com.acme.insurancefeeservice.util.CustomBigDecimalDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "insurance_fee")
public class Insurance implements IData {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Builder.Default
    @JsonProperty("Category")
    private String category = "Generic";

    @Column(precision=15, scale=5)
    @JsonProperty("Lim_low")
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    private BigDecimal limitLow;

    @Column(precision=15, scale=5)
    @JsonProperty("Lim_Top")
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    private BigDecimal limitTop;

    @Column(precision=15, scale=5)
    @JsonProperty("Fee")
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    private BigDecimal fee;

}
