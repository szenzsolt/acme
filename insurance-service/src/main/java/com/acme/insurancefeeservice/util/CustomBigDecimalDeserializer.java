package com.acme.insurancefeeservice.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CustomBigDecimalDeserializer extends JsonDeserializer<BigDecimal> {


    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {
        String value = jsonParser.getText();

        if (value.contains(",")) {
            return new BigDecimal(value.replace(",", ""));
        }
        if (value.toLowerCase().contains("unlimited")) {
            return BigDecimal.valueOf(-1);
        }
        if (value.contains("%")) {
            return new BigDecimal(value.replaceAll("%","")).divide(new BigDecimal(100),5, RoundingMode.CEILING);
        }
        return new BigDecimal(value);
    }
}
