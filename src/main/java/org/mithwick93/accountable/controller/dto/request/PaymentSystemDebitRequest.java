package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.mithwick93.accountable.model.Currency;
import org.mithwick93.accountable.validation.ValidEnum;

import java.math.BigDecimal;

public record PaymentSystemDebitRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 500)
        String description,

        @NotBlank
        @ValidEnum(enumClass = Currency.class, message = "must be a valid currency")
        String currency,

        @NotNull
        int assetId,

        @DecimalMin(value = "0.0", message = "Daily limit must be non-negative")
        BigDecimal dailyLimit
) {

}
