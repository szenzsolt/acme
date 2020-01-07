package com.acme.chemicalproducts.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
public class FeeNotFoundException extends RuntimeException{

    private String productName;

    private String category;

}
