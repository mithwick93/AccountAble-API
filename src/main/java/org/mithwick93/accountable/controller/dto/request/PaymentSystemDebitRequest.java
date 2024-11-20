package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.mithwick93.accountable.model.Currency;

import java.math.BigDecimal;

public record PaymentSystemDebitRequest(
        @NotBlank(message = "Name is required")
        String name,

        String description,

        @NotNull(message = "Currency is required")
        Currency currency,

        @NotNull(message = "Asset id is required")
        int assetId,

        @DecimalMin(value = "0.0", message = "Daily limit must be non-negative")
        BigDecimal dailyLimit
) {

}
