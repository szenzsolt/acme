package com.acme.insurancefeeservice.error;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class FeeNotFoundException extends RuntimeException{

    private long id;

}
