package org.mithwick93.accountable.gateway.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class CurrencyConversionResponse {

    private String result;

    @JsonProperty("conversion_rates")
    private Map<String, Double> conversionRates;

}
