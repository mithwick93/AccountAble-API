package org.mithwick93.accountable.controller.dto.response;

import java.math.BigDecimal;

public record CurrencyConvertResponse(
        String sourceCurrency,
        String targetCurrency,
        BigDecimal sourceAmount,
        BigDecimal targetAmount,
        double conversionRate
) {

}
