package org.mithwick93.accountable.controller.dto.response;

import java.util.Map;

public record ExchangeRateResponse(
        Map<String, Double> conversionRates
) {

}
