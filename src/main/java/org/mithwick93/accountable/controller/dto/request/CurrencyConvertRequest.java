package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.mithwick93.accountable.model.Currency;
import org.mithwick93.accountable.validation.ValidEnum;

import java.math.BigDecimal;

public record CurrencyConvertRequest(
        @NotBlank
        @ValidEnum(enumClass = Currency.class, message = "must be a valid currency")
        String sourceCurrency,

        @NotBlank
        @ValidEnum(enumClass = Currency.class, message = "must be a valid currency")
        String targetCurrency,

        @NotNull
        @DecimalMin(value = "0.0", message = "Source amount must be non-negative")
        BigDecimal sourceAmount
) {

}
